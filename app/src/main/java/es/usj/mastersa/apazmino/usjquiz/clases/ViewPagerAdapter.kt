package es.usj.mastersa.apazmino.usjquiz.clases

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    var fragmentsList: ArrayList<Fragment>? = null
    var fragmentTitleList: ArrayList<String>? = null

    init {
        fragmentsList = ArrayList()
        fragmentTitleList = ArrayList()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList?.get(position)!!
    }

    override fun getCount(): Int {
        return fragmentsList?.size!!
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentsList?.add(fragment)
        fragmentTitleList?.add(title)
    }

    override fun getPageTitle(position: Int): String {
        return fragmentTitleList?.get(position)!!
    }

}