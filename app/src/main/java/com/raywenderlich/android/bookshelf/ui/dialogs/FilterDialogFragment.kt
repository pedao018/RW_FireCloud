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

package com.raywenderlich.android.bookshelf.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.Query
import com.raywenderlich.android.bookshelf.R
import com.raywenderlich.android.bookshelf.model.Book
import com.raywenderlich.android.bookshelf.model.Filters

class FilterDialogFragment : DialogFragment(), View.OnClickListener {

  interface FilterListener {
    fun onFilter(filters: Filters)
  }

  private lateinit var rootView: View
  private lateinit var authorSpinner: Spinner
  private lateinit var categorySpinner: Spinner
  private lateinit var sortSpinner: Spinner
  private lateinit var filterListener: FilterListener

  private val filters: Filters
    get() {
      return Filters(
        selectedCategory,
        selectedAuthor,
        selectedSortBy,
        sortDirection
      )
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    rootView = inflater.inflate(R.layout.dialog_filters, container, false)
    categorySpinner = rootView.findViewById(R.id.spinner_category)
    authorSpinner = rootView.findViewById(R.id.spinner_author)
    sortSpinner = rootView.findViewById(R.id.spinner_sort)
    rootView.findViewById<View>(R.id.button_apply).setOnClickListener(this)
    rootView.findViewById<View>(R.id.button_cancel).setOnClickListener(this)
    return rootView
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is FilterListener) {
      filterListener = context
    }
  }

  override fun onResume() {
    super.onResume()
    dialog!!.window!!.setLayout(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.button_apply -> onApplyClicked()
      R.id.button_cancel -> onCancelClicked()
    }
  }

  private fun onApplyClicked() {
    filterListener.onFilter(filters)
    dismiss()
  }

  private fun onCancelClicked() {
    dismiss()
  }

  fun resetFilters() {
    categorySpinner.setSelection(0)
    authorSpinner.setSelection(0)
    sortSpinner.setSelection(0)
  }

  private val selectedCategory: String?
    get() {
      val selected = categorySpinner.selectedItem as String
      return if (getString(R.string.value_any_category) == selected) {
        null
      } else {
        selected
      }
    }

  private val selectedAuthor: String?
    get() {
      val selected = authorSpinner.selectedItem as String
      return if (getString(R.string.value_any_author) == selected) {
        null
      } else {
        selected
      }
    }

  private val selectedSortBy: String?
    get() {
      val selected = sortSpinner.selectedItem as String
      if (getString(R.string.sort_by_rating) == selected) {
        return Book.FIELD_RATING
      }
      if (getString(R.string.sort_by_year) == selected) {
        return Book.FIELD_YEAR_PUBLISHED
      }
      return if (getString(R.string.sort_alphabetically) == selected) {
        Book.FIELD_TITLE
      } else null
    }

  private val sortDirection: Query.Direction?
    get() {
      val selected = sortSpinner.selectedItem as String
      if (getString(R.string.sort_by_rating) == selected) {
        return Query.Direction.DESCENDING
      }
      if (getString(R.string.sort_by_year) == selected) {
        return Query.Direction.DESCENDING
      }
      return if (getString(R.string.sort_alphabetically) == selected) {
        Query.Direction.ASCENDING
      } else null
    }

  companion object {
    const val TAG = "FilterDialog"
  }
}
