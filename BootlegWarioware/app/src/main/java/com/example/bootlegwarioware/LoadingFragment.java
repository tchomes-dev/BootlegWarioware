package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;

public class LoadingFragment extends Fragment {

    private FragmentLoadingBinding binding;
    private GameViewModel viewModel;

    int i = 0;
    int milliSecCounter = 1500;
    int milliSecInterval= 100;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        binding.progressbar.setProgress(100);

        binding.scoreTextView.setText(String.valueOf(viewModel.points));

        CountDownTimer countDown = new CountDownTimer(milliSecCounter,milliSecInterval) {
            @Override
            public void onTick(long l) {
                i++;
                binding.progressbar.setProgress(100 - (i*100/(milliSecCounter/milliSecInterval)));
            }

            @Override
            public void onFinish() {
                i++;
                binding.progressbar.setProgress(0);
                Navigation.findNavController(view).navigate(R.id.action_loadingFragment_to_demoGameFragment);
            }
        };

        countDown.start();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}