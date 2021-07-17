package ru.androidschool.intensiv.presentation.base

import androidx.annotation.LayoutRes

abstract class BaseMVPFragment<T>(@LayoutRes layoutRes: Int) : BaseFragment(layoutRes) {
    protected abstract val presenter: BasePresenter<T>

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }
}
