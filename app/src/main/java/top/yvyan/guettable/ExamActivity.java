package top.yvyan.guettable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import top.yvyan.guettable.bean.ExamBean;
import top.yvyan.guettable.data.AccountData;
import top.yvyan.guettable.data.GeneralData;
import top.yvyan.guettable.data.MoreDate;
import top.yvyan.guettable.service.StaticService;

public class ExamActivity extends AppCompatActivity {

    private AccountData accountData;
    private GeneralData generalData;
    private MoreDate moreDate;

    private TextView examState;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        accountData = AccountData.newInstance(this);
        generalData = GeneralData.newInstance(this);
        moreDate = MoreDate.newInstance(this);

        examState = findViewById(R.id.exam_state);
        textView = findViewById(R.id.testShow);

        updateView();

        new Thread(() -> {
            List<ExamBean> examBeans;
            StringBuilder cookie_builder = new StringBuilder();
            int state = StaticService.autoLogin(
                    this,
                    accountData.getUsername(),
                    accountData.getPassword(),
                    cookie_builder
            );
            if (state == 0) {
                examBeans = StaticService.getExam(this, cookie_builder.toString(), generalData.getTerm());
                if (examBeans != null) {
                    moreDate.setExamBeans(examBeans);
                    runOnUiThread(() -> {
                        examState.setText("考试安排 更新成功");
                        updateView();
                    });
                }
            } else {
                runOnUiThread(() -> {
                    examState.setText("考试安排 网络错误，从本地导入");
                    updateView();
                });
            }
        }).start();
    }

    /**
     * 更新考试安排视图
     */
    public void updateView() {
        List<ExamBean> examBeans = moreDate.getExamBeans();
        textView.setText(examBeans.toString());
    }
}