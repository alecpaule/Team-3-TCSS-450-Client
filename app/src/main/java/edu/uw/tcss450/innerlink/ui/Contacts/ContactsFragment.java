package edu.uw.tcss450.innerlink.ui.Contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.ui.Chat.ChatMessageRecyclerViewAdapter;

/**
 * A fragment that displays the contact's information after clicking the specific contact
 * card on the list selection fragment.
 */
public class ContactsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ContactsFragmentArgs args = ContactsFragmentArgs.fromBundle(getArguments());
        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());
        binding.textEmail.setText(args.getContactsFragment().getmEmail());
        binding.textFirstName.setText(args.getContactsFragment().getmFirstName());
        binding.textLastName.setText(args.getContactsFragment().getmLastName());
        binding.textUsername.setText(args.getContactsFragment().getmUsername());
    }
}