package com.mugveiga.coinsalert.modules.coindetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mugveiga.coinsalert.data.api.NetworkState
import com.mugveiga.coinsalert.data.api.Status
import com.mugveiga.coinsalert.databinding.FragmentCoinDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

  private val viewModel: CoinDetailViewModel by viewModels()
  private var _binding: FragmentCoinDetailBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {
      if (it.containsKey(ARG_ITEM_ID)) {
        viewModel.id = it.getString(ARG_ITEM_ID)!!
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
    val rootView = binding.root

    viewModel.networkState.observe(viewLifecycleOwner) {
      if (it == NetworkState.LOADING) {
        binding.progressBar.visibility = View.VISIBLE
      } else {
        binding.progressBar.visibility = View.GONE
      }
      if (it.status == Status.FAILED) Snackbar.make(binding.root, it.msg, Snackbar.LENGTH_SHORT).show()
    }

    viewModel.coinPrice.observe(viewLifecycleOwner) {
      binding.itemName.text = it.id
      binding.itemPrice.text = NumberFormat.getCurrencyInstance().format(it.usd)
    }

    return rootView
  }

  companion object {
    const val ARG_ITEM_ID = "item_id"
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
