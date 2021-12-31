/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.bookshelf.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.*
import com.raywenderlich.android.bookshelf.R
import com.raywenderlich.android.bookshelf.databinding.ActivityMainBinding
import com.raywenderlich.android.bookshelf.model.Filters
import com.raywenderlich.android.bookshelf.ui.adapters.BooksAdapter
import com.raywenderlich.android.bookshelf.ui.adapters.SwipeToDeleteCallback
import com.raywenderlich.android.bookshelf.ui.dialogs.FilterDialogFragment
import com.raywenderlich.android.bookshelf.ui.dialogs.UpdateBookDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(),
  FilterDialogFragment.FilterListener, UpdateBookDialogFragment.UpdateBookListener,
  View.OnClickListener {

  private lateinit var binding: ActivityMainBinding

  private lateinit var booksAdapter: BooksAdapter

  private val filterDialog = FilterDialogFragment()
  private lateinit var updateBookDialog : UpdateBookDialogFragment

  private val viewModel: MainViewModel by viewModel()

  private lateinit var documentClicked: DocumentSnapshot

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)

    binding.filterBar.setOnClickListener(this)
    binding.buttonClearFilter.setOnClickListener(this)
    binding.addBooksFab.setOnClickListener(this)

    initRecyclerView()
  }

  override fun onStart() {
    super.onStart()

    onFilter(viewModel.filters)

    booksAdapter.startListening()
  }

  override fun onStop() {
    super.onStop()

    booksAdapter.stopListening()
  }

  private fun initRecyclerView() {
    val onBookClicked = object : (String, DocumentSnapshot) -> Unit {
      override fun invoke(currentTitle: String, document: DocumentSnapshot) {
        updateBookDialog = UpdateBookDialogFragment(currentTitle)

        documentClicked = document

        updateBookDialog.show(supportFragmentManager, UpdateBookDialogFragment.TAG)
      }
    }

    with(binding) {
      booksAdapter = object : BooksAdapter(viewModel.query, onBookClicked) {
        override fun onDataChanged() {
          if (itemCount == 0) {
            booksRecycler.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
          } else {
            booksRecycler.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
          }
        }

        override fun onError(e: FirebaseFirestoreException) {
          emptyView.visibility = View.GONE
          Snackbar.make(
            findViewById(android.R.id.content),
            "Error: ${e.localizedMessage}", Snackbar.LENGTH_LONG
          ).show()
        }
      }
      booksRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
      booksRecycler.adapter = booksAdapter
      val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(booksAdapter))
      itemTouchHelper.attachToRecyclerView(booksRecycler)
    }
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.add_books_fab -> {
        if (binding.addBooksFab.isExtended) {
          binding.addBooksFab.shrink()

          viewModel.addBooksClicked()
        } else {
          binding.addBooksFab.extend()
        }
      }
      R.id.filter_bar -> {
        onFilterClicked()
      }
      R.id.button_clear_filter -> {
        onClearFilterClicked()
      }
    }
  }

  override fun onBookTitleUpdate(newTitle: String) {
    viewModel.updateBook(documentClicked, newTitle)
  }

  override fun onFilter(filters: Filters) {
    viewModel.prepareQuery(filters)

    booksAdapter.setQuery(viewModel.query)

    binding.textCurrentSearch.text = HtmlCompat.fromHtml(
      filters.getSearchDescription(this),
      HtmlCompat.FROM_HTML_MODE_COMPACT
    )

    binding.textCurrentSortBy.text = filters.getOrderDescription(this)
  }

  private fun onFilterClicked() {
    filterDialog.show(supportFragmentManager, FilterDialogFragment.TAG)
  }

  private fun onClearFilterClicked() {
    filterDialog.resetFilters()
    onFilter(Filters.default)
  }

  companion object {
    private const val TAG = "MainActivity"
    private const val RC_SIGN_IN = 9001
    const val BOOKS_COLLECTION = "books"
    const val BOOK_QUERY_LIMIT: Long = 30
  }
}