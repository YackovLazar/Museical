package com.ladswithlaptops.museical.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ladswithlaptops.museical.R;
import com.ladswithlaptops.museical.classes.PlayerViewModel;
import com.ladswithlaptops.museical.classes.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class SongsFragment extends Fragment
{

    private PlayerViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        PlayerViewModel playerViewModel = new ViewModelProvider(this.requireActivity()).get(PlayerViewModel.class);

       // RecyclerView recyclerView = getView().findViewById(R.id.songs_recycler); FIXME
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(playerViewModel.getSongs());
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
