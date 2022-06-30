package uz.gita.numberpuzzle.ui.game;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import uz.gita.numberpuzzle.R;
import uz.gita.numberpuzzle.models.Coordinate;
import uz.gita.numberpuzzle.utils.IsSolvingPuzzle;

public class GameFragment extends Fragment {

    private View root;
    private TextView textScore;
    private Chronometer textTime;
    private Button[][] buttons;
    private ArrayList<Integer> numbers;
    private Coordinate emptyCoordinate;
    private int score;
    IsSolvingPuzzle isSolvingPuzzle;
    private long pauseTime;
    private boolean isStarted;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_game, container, false);
        isSolvingPuzzle = new IsSolvingPuzzle();
        loadViews();
        loadButtons();
        initNumbers();
        loadNumbersToButtons();
        startTimer();
        return root;
    }

    private void startTimer() {
        textTime.setBase(SystemClock.elapsedRealtime());
        textTime.start();
    }

    private void loadViews() {
        root.findViewById(R.id.btn_refresh).setOnClickListener(v -> onRestartGame());
        textScore = root.findViewById(R.id.text_score);
        textTime = root.findViewById(R.id.text_time);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isStarted) {
            textTime.setBase(SystemClock.elapsedRealtime() - pauseTime);
            textTime.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTime = SystemClock.elapsedRealtime() - textTime.getBase();
        textTime.stop();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void loadButtons() {
        ViewGroup group = root.findViewById(R.id.numbers_container);
        int count = group.getChildCount();
        int size = (int) Math.sqrt(count);
        buttons = new Button[size][size];
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            Button button = (Button) view;
            button.setOnTouchListener((v, event) -> {
                onButtonClick(view);
                return true;
            });
            int y = i / size;
            int x = i % size;
            button.setTag(new Coordinate(x, y));
            buttons[y][x] = button;
        }
    }

    private void initNumbers() {
        numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }
    }

    private void loadNumbersToButtons() {
        shuffle();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                int index = i * 4 + j;
                if (index < 15) {
                    buttons[i][j].setText(String.valueOf(numbers.get(index)));
                }
            }
        }
        buttons[3][3].setText("");
        buttons[3][3].setVisibility(View.INVISIBLE);
        emptyCoordinate = new Coordinate(3, 3);
        score = 0;
        textScore.setText(String.valueOf(score));
    }

    private void shuffle() {
        numbers.remove(Integer.valueOf(0));
        Collections.shuffle(numbers);
        if (!isSolvingPuzzle.isSolvable(numbers)) shuffle();
    }

    private void onFinishGame() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void onRestartGame() {
        buttons[emptyCoordinate.getY()][emptyCoordinate.getX()].setVisibility(View.VISIBLE);
        loadNumbersToButtons();
        textTime.setBase(SystemClock.elapsedRealtime());
    }

    private void onButtonClick(View view) {
        Button button = (Button) view;
        Coordinate c = (Coordinate) button.getTag();
        int eX = emptyCoordinate.getX();
        int eY = emptyCoordinate.getY();
        int dX = abs(c.getX() - eX);
        int dY = abs(c.getY() - eY);
        if (dX + dY == 1) {
            score++;
            textScore.setText(String.valueOf("Moves:"+score));
            buttons[eY][eX].setText(button.getText());
            buttons[eY][eX].setVisibility(View.VISIBLE);
            button.setText("");
            buttons[c.getY()][c.getX()].setVisibility(View.INVISIBLE);
            emptyCoordinate = c;
            if (isWin()) {
                showDialog();
            }
        }
    }

    private boolean isWin() {
        if (!(emptyCoordinate.getX() == 3 && emptyCoordinate.getY() == 3)) return false;
        for (int i = 0; i < 15; i++) {
            String s = buttons[i / 4][i % 4].getText().toString();
            if (!s.equals(String.valueOf(i + 1))) return false;
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void showDialog() {


        textTime.stop();



        Dialog dialog = new Dialog(requireContext());
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.result_dialog, null);
        TextView tvStep = view.findViewById(R.id.tv_result_step);
        TextView tvTime = view.findViewById(R.id.tv_result_time);

        ImageView btnClose = view.findViewById(R.id.btn_close);
        ImageView btnCorrect = view.findViewById(R.id.btn_correct);
        ImageView btnNext = view.findViewById(R.id.btn_next);

        btnClose.setOnClickListener(v -> {
            onFinishGame();
            dialog.dismiss();
        });

        btnCorrect.setOnClickListener(v -> {
            onFinishGame();
            dialog.dismiss();
        });

        btnNext.setOnClickListener(v -> {
            dialog.dismiss();
            textTime.setBase(SystemClock.elapsedRealtime());
            textTime.start();
            onRestartGame();
        });

        tvStep.setText("Moves " + score);
        tvTime.setText("Time " + textTime.getText().toString());

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


}