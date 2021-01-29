package es.amunoz.tablegamers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.MethodUtil

class UsersSpinnerAdapter(val context: Context, val users: List<User>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    public val listUserSelected = arrayListOf<User>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_spinner_user, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        view.setOnClickListener(View.OnClickListener {
            var user = users[position]
            if(!listUserSelected.contains(user) || listUserSelected.isEmpty()){
                listUserSelected.add(user)
                vh.ivUserAvatar.setImageDrawable(MethodUtil.getDrawableIcAprobed(users[position].avatar!!,context))

            }else{
                listUserSelected.remove(user)
                vh.ivUserAvatar.setImageDrawable(MethodUtil.getDrawableAvatar(user.avatar!!,context))

            }
        })


        vh.tvUserName.text = users[position].name
        vh.ivUserAvatar.setImageDrawable(MethodUtil.getDrawableAvatar(users[position].avatar!!,context))

        return view
    }

    private fun addUser(user: User){
        if(!listUserSelected.contains(user)){
            listUserSelected.add(user)
        }else{
            listUserSelected.remove(user)
        }
    }
    override fun getItem(position: Int): Any? = users[position];


    override fun getCount(): Int = users.size;


    override fun getItemId(position: Int): Long = position.toLong();


    private class ItemHolder(row: View?) {
        val tvUserName: TextView
        val ivUserAvatar: ImageView

        init {
            tvUserName = row?.findViewById(R.id.item_spinner_user) as TextView
            ivUserAvatar = row?.findViewById(R.id.item_spinner_imagen) as ImageView
        }
    }

}