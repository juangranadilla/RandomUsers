package com.juangm.randomusers.presentation.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.juangm.randomusers.R
import com.juangm.randomusers.domain.models.User
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_users.*
import javax.inject.Inject

class UsersFragment : Fragment(), UserClickInterface {

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var adapter: UsersAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setViewModel()
    }

    override fun showUserDetail(user: User) {
        //TODO navigate to user detail using Navigation
    }

    private fun setRecyclerView() {
        adapter = UsersAdapter(this)
        users_recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        users_recycler.adapter = adapter
    }

    private fun setViewModel() {
        usersViewModel = ViewModelProviders.of(this, viewModelFactory)[UsersViewModel::class.java]
        usersViewModel.users.observe(this, Observer {
                users -> adapter.submitList(users)
        })
    }
}
