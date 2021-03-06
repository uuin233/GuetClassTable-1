package top.yvyan.guettable.service.fetch;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;

import top.yvyan.guettable.Gson.LoginResponse;
import top.yvyan.guettable.Http.Get;
import top.yvyan.guettable.Http.GetBitmap;
import top.yvyan.guettable.Http.HttpConnectionAndCode;
import top.yvyan.guettable.Http.Post;
import top.yvyan.guettable.R;

public class LAN {

    /**
     * 获取验证码
     * @param context context
     * @return        验证码图片
     */
    public static HttpConnectionAndCode checkCode(Context context) {
        Resources resources = context.getResources();
        return GetBitmap.get(
                resources.getString(R.string.lan_get_checkcode_url),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_checkcode_referer),
                null,
                resources.getString(R.string.cookie_delimiter)
        );
    }

    /**
     * 登录
     * @param context   context
     * @param account   学号
     * @param pwd       密码
     * @param checkCode 验证码
     * @param cookie    获取验证码之后的cookie
     * @param builder   用于接收登录后的cookie
     * @return          登录状态
     */
    public static HttpConnectionAndCode login(Context context, String account, String pwd, String checkCode, String cookie, StringBuilder builder) {
        Resources resources = context.getResources();
        String body = "us=" + account + "&pwd=" + pwd + "&ck=" + checkCode;
        HttpConnectionAndCode login_res = Post.post(
                resources.getString(R.string.lan_login_url),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_login_url),
                body,
                cookie,
                "}",
                resources.getString(R.string.cookie_delimiter),
                resources.getString(R.string.lan_login_success_contain_response_text),
                null,
                null,
                null
        );
        if (login_res.code == 0) {
            LoginResponse response = new Gson().fromJson(login_res.comment, LoginResponse.class);
            login_res.comment = response.getMsg();
        }
        if (login_res.code == 0 && builder != null) {
            if (!builder.toString().isEmpty()) {
                builder.append(resources.getString(R.string.cookie_delimiter));
            }
            builder.append(login_res.cookie);
        }
        return login_res;
    }

    /**
     * 获取学生个人信息
     * @param context context
     * @param cookie  登录后的cookie
     * @return        gson格式的个人信息
     */
    public static HttpConnectionAndCode studentInfo(Context context, String cookie) {
        Resources resources = context.getResources();
        return Post.post(
                resources.getString(R.string.lan_get_student_url),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                null,
                cookie,
                "}",
                null,
                resources.getString(R.string.lan_get_student_success_contain_response_text),
                null,
                null,
                null
        );
    }

    /**
     * 获取课程安排
     * @param context context
     * @param cookie  登录后的cookie
     * @return        gson格式的课程安排
     */
    public static HttpConnectionAndCode getClassTable(Context context, String cookie, String term) {
        Resources resources = context.getResources();
        String[] param = {"term=" + term};
        return Get.get(
                resources.getString(R.string.lan_get_table_url),
                param,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_table_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_get_table_success_contain_response_text),
                null,
                null,
                null,
                10000
        );
    }

    /**
     * 获取课内实验安排
     * @param context context
     * @param cookie  登录后的cookie
     * @param term    学期（格式：2020-2021_1）
     * @return        gson格式的课内实验安排
     */
    public static HttpConnectionAndCode getLabTable(Context context, String cookie, String term) {
        Resources resources = context.getResources();
        String[] param = {"term=" + term};
        return Get.get(
                resources.getString(R.string.lan_get_lab_table_url),
                param,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_table_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_get_table_success_contain_response_text),
                null,
                null,
                null,
                30000
        );
    }

    /**
     * 获取考试安排
     * @param context context
     * @param cookie  登录后的cookie
     * @param term    学期（格式：2020-2021_1）
     * @return        gson格式的考试安排
     */
    public static HttpConnectionAndCode getExam(Context context, String cookie, String term) {
        Resources resources = context.getResources();
        String[] param = {"term=" + term};
        return Get.get(
                resources.getString(R.string.lan_get_exam_url),
                param,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_login_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_get_table_success_contain_response_text),
                null,
                null,
                null,
                null
        );
    }

    /**
     * 获取等级考试成绩
     * @param context context
     * @param cookie  登录后的cookie
     * @return        gson格式的等级考试成绩
     */
    public static HttpConnectionAndCode getCET(Context context, String cookie) {
        Resources resources = context.getResources();
        return Get.get(
                resources.getString(R.string.lan_get_cet_url),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_get_table_success_contain_response_text),
                null,
                null,
                null,
                null
        );
    }

    /**
     * 获取普通考试成绩
     * @param context context
     * @param cookie  登录后的cookie
     * @return        gson格式的普通考试成绩
     */
    public static HttpConnectionAndCode getExamScore(Context context, String cookie) {
        Resources resources = context.getResources();
        return Get.get(
                resources.getString(R.string.lan_get_examscore_url),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_get_table_success_contain_response_text),
                null,
                null,
                null,
                null
        );
    }

    /**
     * 获取实验考试成绩
     * @param context context
     * @param cookie  登录后的cookie
     * @return        gson格式的实验考试成绩
     */
    public static HttpConnectionAndCode getExperimentScore(Context context, String cookie) {
        Resources resources = context.getResources();
        return Get.get(
                resources.getString(R.string.lan_get_experimentscore_url),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_get_table_success_contain_response_text),
                null,
                null,
                null,
                null
        );
    }

    /**
     * 获取当前学期
     * @param context context
     * @param cookie  登录后的cookie
     * @return        gson格式的当前学期
     */
    public static HttpConnectionAndCode getThisTerm(Context context, String cookie) {
        Resources resources = context.getResources();
        return Post.post(
                resources.getString(R.string.lan_get_this_term),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                null,
                cookie,
                null,
                resources.getString(R.string.cookie_delimiter),
                resources.getString(R.string.lan_login_success_contain_response_text),
                null,
                null,
                null
        );
    }

    /**
     * 获取评价教师列表
     * @param context context
     * @param cookie  登录后的cookie
     * @param term    学期（格式：2020-2021_1）
     * @return        gson格式的教师列表
     */
    public static HttpConnectionAndCode getTeacherList(Context context, String cookie, String term) {
        Resources resources = context.getResources();
        String[] param = {"term=" + term};
        return Get.get(
                resources.getString(R.string.lan_get_teacher_list),
                param,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_login_success_contain_response_text),
                null,
                null,
                null,
                10000
        );
    }

    /**
     * 获取某个老师的评价表单
     * @param context   context
     * @param cookie    登录后的cookie
     * @param term      学期（格式：2020-2021_1）
     * @param courseNo  课程编号
     * @param teacherNo 老师编号
     * @return           gson格式的老师评价表单
     */
    public static HttpConnectionAndCode getAvgTeacherForm(Context context, String cookie, String term, String courseNo, String teacherNo) {
        Resources resources = context.getResources();
        String[] params = {
                "term=" + term,
                "courseno=" + courseNo,
                "teacherno=" + teacherNo
        };
        return Get.get(
                resources.getString(R.string.lan_get_avg_thacher_data),
                params,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_table_referer),
                cookie,
                "]}",
                null,
                resources.getString(R.string.lan_login_success_contain_response_text),
                null,
                null,
                null,
                null
        );
    }

    /**
     * 提交老师评价表单
     * @param context   context
     * @param cookie    登录后的cookie
     * @param term      学期（格式：2020-2021_1）
     * @param courseNo  课程编号
     * @param teacherNo 老师编号
     * @param postBody  评价表单
     * @return          结果
     */
    public static HttpConnectionAndCode saveTeacherForm(Context context, String cookie, String term, String courseNo, String teacherNo, String postBody) {
        Resources resources = context.getResources();
        String[] params = {
                "term=" + term,
                "courseno" + courseNo,
                "teacherno" + teacherNo
        };
        return Post.post(
                resources.getString(R.string.lan_save_avg_teacher_data),
                params,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                postBody,
                cookie,
                "}",
                null,
                resources.getString(R.string.lan_login_success_contain_response_text),
                null,
                null,
                "application/json"
        );
    }

    /**
     * 提交评价老师总评
     * @param context   context
     * @param cookie    登录后的cookie
     * @param postBody  请求体
     * @return          操作结果
     */
    public static HttpConnectionAndCode commitTeacherForm(Context context, String cookie, String postBody) {
        Resources resources = context.getResources();
        return Post.post(
                resources.getString(R.string.lan_commit_avg_teacher_data),
                null,
                resources.getString(R.string.user_agent),
                resources.getString(R.string.lan_get_student_referer),
                postBody,
                cookie,
                "}",
                null,
                resources.getString(R.string.lan_login_success_contain_response_text),
                null,
                null,
                null
        );
    }
}
