package edu.uw.tcss450.innerlink.ui.Chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.databinding.FragmentChatRoomListBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Chat screen where all of a user's active Chat Rooms are listed and displayed.
 */
// TODO: "E/RecyclerView: No adapter attached; skipping layout" when first entering ChatRoomFragment
// TODO: "E/Chat message already received: Or duplicate..."
public class ChatRoomListFragment extends Fragment {
    private ChatRoomViewModel mChatRoomModel;
    private UserInfoViewModel mUserModel;

    // The chat ID for "global" chat
    private static final int HARD_CODED_CHAT_ID = 1;

    public ChatRoomListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Returns an existing instance of the ViewModel if it exists, otherwise creates
        // a new one.
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatRoomModel = provider.get(ChatRoomViewModel.class);
        mChatRoomModel.getFirstMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat_room_list, container, false);

        rootView.setTag("RecyclerViewFragment");
        RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.list_root);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        // TODO: "E/Populate chatIds list with actual chatId values that the user has.
        List chatIds = new ArrayList<>();
        chatIds.add(HARD_CODED_CHAT_ID);

        ChatRoomRecyclerViewAdapter adapter = new ChatRoomRecyclerViewAdapter(chatIds);;
        recycler.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatRoomListBinding binding = FragmentChatRoomListBinding.bind(getView());
        mChatRoomModel.addChatRoomListObserver(getViewLifecycleOwner(), chatRoomList -> {
            if (!chatRoomList.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatRoomRecyclerViewAdapter(chatRoomList));
            }
        });
    }
}