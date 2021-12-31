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
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.raywenderlich.android.bookshelf.R

class UpdateBookDialogFragment(private val currentTitle: String) : DialogFragment(),
  View.OnClickListener {

  interface UpdateBookListener {
    fun onBookTitleUpdate(newTitle: String)
  }

  private lateinit var rootView: View
  private lateinit var bookNameInput: EditText

  private lateinit var updateBookListener: UpdateBookListener

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    rootView = inflater.inflate(R.layout.dialog_update_book, container, false)
    rootView.findViewById<View>(R.id.button_update).setOnClickListener(this)
    rootView.findViewById<View>(R.id.button_cancel).setOnClickListener(this)
    bookNameInput = rootView.findViewById(R.id.book_name_input)
    return rootView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    bookNameInput.setText(currentTitle)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is UpdateBookListener) {
      updateBookListener = context
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
      R.id.button_update -> onUpdateClicked()
      R.id.button_cancel -> onCancelClicked()
    }
  }

  private fun onUpdateClicked() {
    updateBookListener.onBookTitleUpdate(bookNameInput.text.toString())
    dismiss()
  }

  private fun onCancelClicked() {
    dismiss()
  }

  companion object {
    const val TAG = "UpdateBookDialog"
  }
}
