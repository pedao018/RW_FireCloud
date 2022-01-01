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

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*

abstract class FirestoreAdapter<VH : RecyclerView.ViewHolder?>(
  private var query: Query?
) : RecyclerView.Adapter<VH>(), EventListener<QuerySnapshot> {

  private var registration: ListenerRegistration? = null
  private val snapshots = ArrayList<DocumentSnapshot>()

  open fun onDataChanged() {}
  open fun onError(e: FirebaseFirestoreException) {}

  override fun getItemCount(): Int {
    return snapshots.size
  }

  override fun onEvent(documentSnapshots: QuerySnapshot?, error: FirebaseFirestoreException?) {
    if (error != null) {
      Log.e(TAG, "onEvent: error", error)
      onError(error)
      return
    }
    if (documentSnapshots == null) return
    for (change in documentSnapshots.documentChanges) {
      when (change.type) {
        DocumentChange.Type.ADDED -> {
          //Log.e("DocumentChange", "Document Added!")
          onDocumentAdded(change)
        }
        DocumentChange.Type.MODIFIED -> {
          //Log.e("DocumentChange", "Document Modified!")
          onDocumentModified(change)
        }

        DocumentChange.Type.REMOVED -> {
          //Log.e("DocumentChange", "Document Removed!")
          onDocumentRemoved(change)
        }
      }
    }
  }

  fun setQuery(query: Query?) {
    // Stop listening
    stopListening()

    // Clear existing data
    snapshots.clear()
    notifyDataSetChanged()

    // Listen to new query
    this.query = query

    startListening()
  }

  fun getSnapshot(index: Int): DocumentSnapshot {
    return snapshots[index]
  }

  private fun onDocumentAdded(change: DocumentChange) {
    snapshots.add(change.newIndex, change.document)
    notifyItemInserted(change.newIndex)
  }

  private fun onDocumentModified(change: DocumentChange) {
    if (change.oldIndex == change.newIndex) {
      //Item changed but remained in the same position
      snapshots[change.oldIndex] = change.document
      notifyItemChanged(change.oldIndex)
    } else {
      //Item changed and has changed position
      snapshots.removeAt(change.oldIndex)
      snapshots.add(change.newIndex, change.document)
      notifyItemMoved(change.oldIndex, change.newIndex)
    }
  }

  private fun onDocumentRemoved(change: DocumentChange) {
    snapshots.removeAt(change.oldIndex)
    notifyItemRemoved(change.oldIndex)
  }

  fun startListening() {
    if (query != null && registration == null) {
      registration = query?.addSnapshotListener(this)
    }
  }

  fun stopListening() {
    registration?.remove()
    registration = null

    snapshots.clear()
    notifyDataSetChanged()
  }

  companion object {
    private const val TAG = "Firestore Adapter"
  }
}