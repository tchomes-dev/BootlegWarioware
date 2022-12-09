package com.example.bootlegwarioware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bootlegwarioware.databinding.FragmentGameResultsBinding;
import com.example.bootlegwarioware.databinding.FragmentLoadingBinding;


public class GameResultsFragment extends Fragment {

    private FragmentGameResultsBinding binding;
//    private GameViewModelFactory viewModelFactory;

    int i = 0;
    int milliSecCounter = 2000;
    int milliSecInterval= 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameResultsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        boolean isGameCompleted = GameResultsFragmentArgs.fromBundle(requireArguments()).getIsGameCompleted();

        // Lose life if Game wasn't completed
        if (!isGameCompleted){
            ((MainActivity)getContext()).loseLife();
        }

        binding.scoreTextView.setText(String.valueOf(((MainActivity)getContext()).getScore()));
        updateLifeSymbols(((MainActivity)getContext()).getLivesLeft());

        binding.progressbar.setProgress(100);
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

                // Check if Game Over, otherwise continue with the game
                if (((MainActivity)getContext()).isGameOver()){
                    System.out.println("Made it HERE");
                    NavDirections action = GameResultsFragmentDirections.actionGameResultsFragmentToGameOverFragment(((MainActivity)getContext()).getScore());
                    Navigation.findNavController(view).navigate(action);
                } else {
                    ((MainActivity)getContext()).incrementScore();
                    Navigation.findNavController(view).navigate(R.id.action_gameResultsFragment_to_loadingFragment);
                }
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

    public void updateLifeSymbols(int livesLeft){
        switch(livesLeft) {
            case 0:
                binding.life1ImageView.setVisibility(View.INVISIBLE);
            case 1:
                binding.life2ImageView.setVisibility(View.INVISIBLE);
            case 2:
                binding.life3ImageView.setVisibility(View.INVISIBLE);
            case 3:
                binding.life4ImageView.setVisibility(View.INVISIBLE);
                break;
            default:
                System.out.println("ERROR here");
                break;
        }
    }


}