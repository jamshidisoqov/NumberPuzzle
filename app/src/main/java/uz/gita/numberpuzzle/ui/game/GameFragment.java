package uz.gita.numberpuzzle.ui.game;

import static java.lang.Math.abs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import uz.gita.numberpuzzle.R;
import uz.gita.numberpuzzle.models.Coordinate;

public class GameFragment extends Fragment {

    private View root;
    private TextView textScore;
    private TextView textTime;
    private Button[][] buttons;
    private ArrayList<Integer> numbers;
    private Coordinate emptyCoordinate;
    private int score;
    private int timer;
    Timer time;
    TimerTask timerTask;
    CountDownTimer countDownTimer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_game, container, false);
        loadViews();
        loadButtons();
        initNumbers();
        loadNumbersToButtons();
        time = new Timer();
        startTimer();
        return root;
    }


    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        setTime();
                    }
                });
            }
        };
        time.schedule(timerTask, 0, 1000);
    }


    private void loadViews() {
        root.findViewById(R.id.btn_refresh).setOnClickListener(v -> onRestartGame());
        textScore = root.findViewById(R.id.text_score);
        textTime = root.findViewById(R.id.text_time);
    }

    private void loadButtons() {
        ViewGroup group = root.findViewById(R.id.numbers_container);
        int count = group.getChildCount();
        int size = (int) Math.sqrt(count);
        buttons = new Button[size][size];
        for (int i = 0; i < count; i++) {
            View view = group.getChildAt(i);
            Button button = (Button) view;
            button.setOnClickListener(this::onButtonClick);
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
        Collections.shuffle(numbers);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                int index = i * 4 + j;
                if (index < 15) {
                    buttons[i][j].setText(String.valueOf(numbers.get(index)));
                }
            }
        }
        buttons[3][3].setText("");
        emptyCoordinate = new Coordinate(3, 3);
        score = 0;
        textScore.setText(String.valueOf(score));
    }

    private void onFinishGame() {

    }

    private void onRestartGame() {
        loadNumbersToButtons();
        timer = 0;
        setTime();
    }

    private void onButtonClick(View view) {
        Button button = (Button) view;
        Coordinate c = (Coordinate) button.getTag();
        Log.d("TTT", "x=" + c.getX() + " y=" + c.getY());
        int eX = emptyCoordinate.getX();
        int eY = emptyCoordinate.getY();
        int dX = abs(c.getX() - eX);
        int dY = abs(c.getY() - eY);
        if (dX + dY == 1) {
            score++;
            textScore.setText(String.valueOf(score));
            buttons[eY][eX].setText(button.getText());
            button.setText("");
            emptyCoordinate = c;
            if (isWin()) {
                onRestartGame();
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
    private void setTime() {
        int minDivide = timer / 60;
        String min = (minDivide < 10) ? "0" + minDivide : minDivide + "";
        textTime.setText(min + ":" + ((timer % 60 < 10) ? "0" + (timer % 60) : timer % 60 + ""));
        timer++;
    }

}