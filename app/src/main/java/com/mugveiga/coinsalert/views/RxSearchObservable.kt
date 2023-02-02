package com.mugveiga.coinsalert.views

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxSearchObservable {
  fun fromView(searchView: SearchView): Observable<String> {
    val subject: PublishSubject<String> = PublishSubject.create()
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(s: String?): Boolean {
        subject.onComplete()
        return true
      }

      override fun onQueryTextChange(text: String?): Boolean {
        subject.onNext(text)
        return true
      }
    })
    return subject
  }
}
