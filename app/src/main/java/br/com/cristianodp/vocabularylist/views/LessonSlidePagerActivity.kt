package br.com.cristianodp.vocabularylist.views

import android.annotation.SuppressLint
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import br.com.cristianodp.vocabularylist.R
import br.com.cristianodp.vocabularylist.ado.CardADO
import br.com.cristianodp.vocabularylist.ado.IFirebaseDatabaseADO
import br.com.cristianodp.vocabularylist.global.getPathCards
import br.com.cristianodp.vocabularylist.models.Card
import kotlinx.android.synthetic.main.activity_lesson_play.*
import android.view.View


/**
 * Created by crist on 21/02/2018.
 */

class LessonSlidePagerActivity : FragmentActivity() {

    private lateinit var collectionId:String
    private lateinit var lessonId:String
    private lateinit var userId:String
    private lateinit var mCardADO: CardADO

    private var mPagerAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_play)

        userId =  intent.getStringExtra("userId")
        collectionId =  intent.getStringExtra("collectionId")
        lessonId =  intent.getStringExtra("lessonId")

        initListenners()
    }

    private fun initListenners() {
        mCardADO = CardADO(getPathCards(userId,collectionId,lessonId), IFirebaseDatabaseADO.TypeListner.MULTIPLE,object : IFirebaseDatabaseADO.IDataChange{
            @SuppressLint("SetTextI18n")
            override fun notifyDataChanged() {
                //
                viewPagerCard.setPageTransformer(true, ZoomOutPageTransformer())
                mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager,mCardADO.list)
                viewPagerCard.adapter = mPagerAdapter
            }
        })

        viewPagerCard.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                var page = (position+1).toString()+"/"+mCardADO.list.size.toString()
                textViewStatus.text = page
            }
        })

    }

    override fun onBackPressed() {
        if (viewPagerCard.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPagerCard.currentItem = viewPagerCard.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager,var list:ArrayList<Card>) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            return CardsFragment().newInstance(list[position])
        }

        override fun getCount(): Int {
            return list.size
        }


    }

    class ZoomOutPageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {


            val pageWidth = view.getWidth()
            val pageHeight = view.getHeight()

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2)
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2)
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor)
                view.setScaleY(scaleFactor)

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }

        companion object {
            private val MIN_SCALE = 0.85f
            private val MIN_ALPHA = 0.5f
        }
    }


}


