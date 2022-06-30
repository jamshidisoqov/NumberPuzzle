package uz.gita.numberpuzzle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uz.gita.numberpuzzle.R;
import uz.gita.numberpuzzle.ui.game.GameFragment;

public class HomeFragment extends Fragment {

    private View root;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        startGame();
        return root;
    }
    private void startGame() {
        root.findViewById(R.id.play_container).setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, new GameFragment()).addToBackStack("game").commit());
    }


}