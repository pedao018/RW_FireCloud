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

package com.raywenderlich.android.bookshelf.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.raywenderlich.android.bookshelf.R
import com.raywenderlich.android.bookshelf.model.Book
import me.zhanghai.android.materialratingbar.MaterialRatingBar

open class BooksAdapter(
  query: Query?,
  private val onClickListener: (String, DocumentSnapshot) -> Unit
) : FirestoreAdapter<BooksAdapter.ViewHolder?>(query) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.item_book, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getSnapshot(position), onClickListener)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var imageView: ImageView = itemView.findViewById(R.id.book_item_image)
    private var titleView: TextView = itemView.findViewById(R.id.book_item_title)
    private var authorView: TextView = itemView.findViewById(R.id.book_item_author)
    private var yearView: TextView = itemView.findViewById(R.id.book_item_year_published)
    private var categoryView: TextView = itemView.findViewById(R.id.book_item_category)
    private var ratingBar: MaterialRatingBar = itemView.findViewById(R.id.book_item_rating)

    fun bind(snapshot: DocumentSnapshot, onClickListener: (String, DocumentSnapshot) -> Unit) {
      val book: Book? = snapshot.toObject(Book::class.java)

      if (book != null) {
        Glide.with(imageView.context)
          .load(book.coverArt)
          .into(imageView)

        titleView.text = book.title

        authorView.text = book.author

        ratingBar.rating = book.rating

        categoryView.text = book.category

        yearView.text = book.yearPublished.toString()

        itemView.setOnClickListener {
          onClickListener(book.title, snapshot)
        }
      }
    }
  }
}
