package com.raywenderlich.android.bookshelf.ui.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.raywenderlich.android.bookshelf.ui.main.MainActivity

class SwipeToDeleteCallback(
  private val adapter: BooksAdapter
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean = false

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    FirebaseFirestore.getInstance()
      .collection(MainActivity.BOOKS_COLLECTION)
      .document(adapter.getSnapshot(viewHolder.bindingAdapterPosition).id)
      .delete()
  }
}