package com.mugveiga.coinsalert.modules.coinlist

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mugveiga.coinsalert.R
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.api.Status
import com.mugveiga.coinsalert.data.model.Coin
import com.mugveiga.coinsalert.databinding.CoinListContentBinding
import com.mugveiga.coinsalert.databinding.FragmentCoinListBinding
import com.mugveiga.coinsalert.modules.coindetail.CoinDetailFragment
import com.mugveiga.coinsalert.views.RxSearchObservable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListFragment : Fragment() {

  private val viewModel: CoinListViewModel by viewModels()
  private var _binding: FragmentCoinListBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentCoinListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.coinList.observe(viewLifecycleOwner) {
      binding.coinListView.adapter = CoinListItemViewAdapter(it)
      binding.swipeRefreshLayout.isRefreshing = false
    }

    viewModel.networkState.observe(viewLifecycleOwner) {
      if (it == NetworkState.LOADING) {
        binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefreshLayout.isRefreshing = true
      } else {
        binding.progressBar.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
      }
      if (it.status == Status.FAILED) Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
    }

    binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

    setupMenu()
  }

  private fun setupMenu() {
    (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val searchView = menuItem.actionView as SearchView
        viewModel.filterCoins(RxSearchObservable.fromView(searchView))
        return true
      }
    }, viewLifecycleOwner, Lifecycle.State.RESUMED)
  }

  class CoinListItemViewAdapter(
    private val values: List<Coin>
  ) :
    RecyclerView.Adapter<CoinListItemViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = CoinListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = values[position]
      holder.coinNameView.text = item.name
      with(holder.itemView) {
        tag = item
        setOnClickListener { itemView ->
          val thisItem = itemView.tag as Coin
          val bundle = Bundle()
          bundle.putString(
            CoinDetailFragment.ARG_ITEM_ID,
            thisItem.id
          )
          itemView.findNavController().navigate(R.id.show_coin_detail, bundle)
        }
      }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: CoinListContentBinding) : RecyclerView.ViewHolder(binding.root) {
      val coinNameView: TextView = binding.coinName
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
