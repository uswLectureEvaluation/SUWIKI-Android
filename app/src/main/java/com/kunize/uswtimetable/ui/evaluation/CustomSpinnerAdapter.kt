package com.kunize.uswtimetable.ui.evaluation

import android.content.Context
import android.widget.ArrayAdapter
import com.kunize.uswtimetable.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CustomSpinnerAdapter(
    var mContext: Context,
    private var spinnerNames: List<String>,
    private var spinnerImages: List<Int>
) : ArrayAdapter<String?>(
    mContext, R.layout.item_spinner_evaluation
) {
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return spinnerNames.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        var mViewHolder = ViewHolder()
        if (convertView == null) {
            val mInflater =
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = mInflater.inflate(R.layout.item_spinner_evaluation, parent, false)
            mViewHolder.mImage =
                convertView.findViewById<View>(R.id.imageview_spinner_image) as ImageView
            mViewHolder.mName =
                convertView.findViewById<View>(R.id.textview_spinner_name) as TextView
            convertView.tag = mViewHolder
        } else {
            mViewHolder = convertView.tag as ViewHolder
        }
        mViewHolder.mImage!!.setImageResource(spinnerImages[position])
        mViewHolder.mName!!.text = spinnerNames[position]
        return convertView!!
    }

    private class ViewHolder {
        var mImage: ImageView? = null
        var mName: TextView? = null
    }
}