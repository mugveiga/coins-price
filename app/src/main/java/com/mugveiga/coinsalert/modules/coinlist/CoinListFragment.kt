package com.mugveiga.coinsalert.modules.coinlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mugveiga.coinsalert.CoinDetailFragment
import com.mugveiga.coinsalert.R
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.api.Status
import com.mugveiga.coinsalert.data.model.Coin
import com.mugveiga.coinsalert.databinding.CoinListContentBinding
import com.mugveiga.coinsalert.databinding.FragmentCoinListBinding
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

    val recyclerView: RecyclerView = binding.coinList

    // Leaving this not using view binding as it relies on if the view is visible the current
    // layout configuration (layout, layout-sw600dp)
    val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

    viewModel.coinList.observe(viewLifecycleOwner, Observer {
      recyclerView.adapter = SimpleItemRecyclerViewAdapter(it, itemDetailFragmentContainer)
    })

    viewModel.networkState.observe(viewLifecycleOwner, Observer {
      binding.progressHorizontal?.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
      if (it.status == Status.FAILED) Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
    })
  }


  class SimpleItemRecyclerViewAdapter(
    private val values: List<Coin>,
    private val itemDetailFragmentContainer: View?
  ) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = CoinListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = values[position]
      holder.idView.text = item.id
      holder.contentView.text = item.name
      with(holder.itemView) {
        tag = item
        setOnClickListener { itemView ->
          val thisItem = itemView.tag as Coin
          val bundle = Bundle()
          bundle.putString(
            CoinDetailFragment.ARG_ITEM_ID,
            thisItem.id
          )
          if (itemDetailFragmentContainer != null) {
            itemDetailFragmentContainer.findNavController()
              .navigate(R.id.fragment_item_detail, bundle)
          } else {
            itemView.findNavController().navigate(R.id.show_coin_detail, bundle)
          }
        }
      }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: CoinListContentBinding) : RecyclerView.ViewHolder(binding.root) {
      val idView: TextView = binding.idText
      val contentView: TextView = binding.content
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
