package uz.gita.numberpuzzle.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uz.gita.numberpuzzle.R;
import uz.gita.numberpuzzle.ui.game.GameFragment;
import uz.gita.numberpuzzle.utils.ButtonClickEffect;
import uz.gita.numberpuzzle.utils.MediaPlayerButton;

public class HomeFragment extends Fragment {

    private View root;
    FrameLayout frameLayout;
    MediaPlayerButton mediaPlayerButton;
    ImageView btnSettings, btnInfo, btnMusic;
    ButtonClickEffect effect;
    public boolean isPause;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();
        effect = new ButtonClickEffect(getContext());
        if (mediaPlayerButton == null) {
            mediaPlayerButton = MediaPlayerButton.getInstance();
            mediaPlayerButton.soundGame(requireContext());
        }
        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initialize() {

        frameLayout = root.findViewById(R.id.play_container);
        btnSettings = root.findViewById(R.id.btn_settings);
        btnInfo = root.findViewById(R.id.btn_info);
        btnMusic = root.findViewById(R.id.btn_music);

        frameLayout.setOnTouchListener((v, event) -> {
            effect.effect(v, event);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Bundle bundle = new Bundle();
                Fragment fragment = new GameFragment();
                fragment.setArguments(bundle);
                bundle.putBoolean("pause", isPause);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_layout, fragment)
                        .addToBackStack("game")
                        .commit();
            }
            return true;
        });

        btnSettings.setOnTouchListener((v, event) -> {
            effect.effect(v, event);
            return true;
        });
        btnInfo.setOnTouchListener((v, event) -> {
            effect.effect(v, event);
            return true;
        });
        btnMusic.setOnTouchListener((v, event) -> {
            effect.effect(v, event);

            if (event.getAction() == MotionEvent.ACTION_UP) {
                isPause = !isPause;
                if (isPause) {
                    btnMusic.setImageResource(R.drawable.pause);
                    mediaPlayerButton.stopPlayer();
                } else {
                    btnMusic.setImageResource(R.drawable.music);
                    mediaPlayerButton.soundGame(getContext());
                }
            }

            return true;
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}