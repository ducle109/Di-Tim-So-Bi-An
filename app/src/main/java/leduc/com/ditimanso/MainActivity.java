package leduc.com.ditimanso;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView        txtScore;
    private Button          btnStart;
    private TableLayout     tbLayout;
    private int             rdNumber;
    private int             score = 5;
    private List<Button>    buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomNumber();
        init();

        // click vào nút new game
        btnStart.setOnClickListener(this);


    }

    // hàm click vào nút new game
    @Override
    public void onClick(View view) {
        // khởi tạo lại số ngẫu nhiên
        randomNumber();

        //reset lại điểm số
        score = 5;
        txtScore.setText("Score " + score + ": ");

        for(int i = 0; i < buttonList.size(); i++) {
            // set các button về trạng thái click đc
            buttonList.get(i).setClickable(true);

            // set color cho button
            buttonList.get(i).setTextColor(Color.parseColor("#0B0B61"));
        }
    }

    // hàm khởi tạo gtri
    public void init() {
        tbLayout    = (TableLayout) findViewById(R.id.tbLayout);
        txtScore    = (TextView) findViewById(R.id.txtScore);
        btnStart    = (Button) findViewById(R.id.btnStart);

        // tạo 1 list để chứa các button
        buttonList  = new ArrayList<Button>();

        txtScore.setText("Score " + score + ": ");

        // function create buttons
        createTableButton();

    }

    // hàm random số ngẫu nhiên
    public void randomNumber() {
        Random random = new Random();
        // các số từ 1 -> 40
        rdNumber = 1 + random.nextInt(40);
        showMsg(rdNumber+"");
    }

    // hàm khởi tạo các button
    public void createTableButton() {
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ép kiểu về dạng button để sử dụng đc các tính năng như kiểu button
                Button btnView = (Button) v;

                // lấy ra chỉ số button đang đc click
                int number = Integer.parseInt((String) btnView.getText());

                // gán chỉ số button hiện tại + them 5 vào fadeNumber1
                int fadeNumber1 = (number + 5);
                // gán chỉ số button hiện tại - đi 5 vào fadeNumber2
                int fadeNumber2 = (number - 5);

                if(number > rdNumber) {
                    // kiểm tra xem nếu button click htai + 5 > hơn chỉ số mảng thì gán lại
                    if(fadeNumber1  > buttonList.size()) {
                        fadeNumber1 = buttonList.size();
                    }
                    // disable 5 button liên tiếp
                    for (int i = (number - 1); i < fadeNumber1; i++) {
                        disableButton(buttonList.get(i));
                    }
                } else if(number < rdNumber) {
                    // kiểm tra xem nếu button click htai - 5 < hơn 0
                    if(fadeNumber2  < 0) {
                        fadeNumber2 = 0;
                    }
                    // disable 5 button liên tiếp
                    for (int i = (number - 1); i >= fadeNumber2; i--) {
                        disableButton(buttonList.get(i));
                    }

                } else {
                    for(int i = 0; i < buttonList.size(); i++) {
                        disableButton(buttonList.get(i));
                    }
                    btnView.setTextColor(Color.parseColor("#F11706"));
                }
                // tính điểm
                if(score-- <= 1) {
                    for(int i = 0; i < buttonList.size(); i++) {
                        disableButton(buttonList.get(i));
                    }
                    buttonList.get(rdNumber-1).setTextColor(Color.parseColor("#F11706"));
                    txtScore.setText("Game Over!");
                } else {
                    txtScore.setText("Score: " + score );
                }
            }
        };

        // khoi tao table
        for(int i = 1; i <= 8; i++) {
            TableRow tableRow = new TableRow(this);
            for(int j = 1; j <= 5; j++) {
                Button button = new Button(this);

                // khi click vào các button
                button.setOnClickListener(btnClick);

                // công thức in ra các số từ 1 --> 40
                int buttonNumber = (5*(i-1) + j);

                button.setText(buttonNumber+ "");
                button.setTextColor(Color.parseColor("#0B0B61"));

                // them button vao mang list
                buttonList.add(button);

                // them button vao table
                tableRow.addView(button);

            }
            tbLayout.addView(tableRow);
        }


    }

    public void disableButton(Button btn) {
        btn.setClickable(false);
        btn.setTextColor(Color.parseColor("#B8CCCE"));
    }


    public void showMsg(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
