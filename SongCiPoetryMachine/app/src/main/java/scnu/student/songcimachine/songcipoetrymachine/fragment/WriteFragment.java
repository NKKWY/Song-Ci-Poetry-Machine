package scnu.student.songcimachine.songcipoetrymachine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import scnu.student.songcimachine.songcipoetrymachine.R;
import scnu.student.songcimachine.songcipoetrymachine.utils.DigitUtil;
import scnu.student.songcimachine.songcipoetrymachine.MainActivity;

/**
 * Created by 52347 on 2016/7/3.
 */
public class WriteFragment extends Fragment
{
    private Spinner mSpinner;
    private ArrayList<String> mList;
    private ArrayAdapter mAdapter;
    private EditText editText;

    // 生成结果
    private TextView tv_result;
    // 词牌名
    private TextView tv_cipai;
    // 题目
    private TextView tv_title;
    // 宋词
    private TextView tv_content;

    private Button goButton;
    // 词牌名
    private String selectedCiPaiName;

    // 补充结果
    private String finalResult;
    // 生成结果
    private String curResult;

    private final String TITLE = "随机";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_write_poems, container, false);
        init(rootView);

        return rootView;
    }

    private void init(View view)
    {
        editText = (EditText)view.findViewById(R.id.et_input);
        goButton = (Button)view.findViewById(R.id.btn_go);
        tv_result = (TextView)view.findViewById(R.id.tv_result);
        tv_cipai = (TextView)view.findViewById(R.id.tv_poem_title);
        tv_title = (TextView)view.findViewById(R.id.tv_content1);
        tv_content = (TextView)view.findViewById(R.id.tv_content2);

        goButton.setOnClickListener(onClickListener);
        selectedCiPaiName = "清平乐";

        mSpinner = (Spinner) view.findViewById(R.id.sn_poems);
        mList = new ArrayList<String>();
        mList.add("清平乐");
        mList.add("浣溪沙");
        mList.add("菩萨蛮");
        mAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, mList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCiPaiName = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.btn_go:
                    DigitUtil digitUtil = new DigitUtil(editText.getText().toString(),
                            getFormatWithCiPaiName(selectedCiPaiName),
                            ((MainActivity) getActivity()).oneDBManager,
                            ((MainActivity) getActivity()).twoDBManager);
                    finalResult = digitUtil.getSentences();//算法生成结果
                    curResult = digitUtil.getUserResult();//生成结果
                    // 显示宋词
                    tv_result.setText(curResult);
                    tv_cipai.setText(selectedCiPaiName);
                    tv_title.setText(TITLE);
                    tv_content.setText(finalResult);

                    Log.d("digitutil", finalResult);
                    Log.d("digitutil", "===============");
                    Log.d("digitutil", curResult);
                    break;
            }
        }
    };

    // 获取ciPaiName对应的词的格式（返回Map，其中numOfWords键对应总字数，numOfSentences键对应总句数，"1","2"..."numOfSentences"键分别
    // 对应每一句的字数）
    private Map getFormatWithCiPaiName(String ciPaiName)
    {
        Map<String, Integer> map = new HashMap();
        switch (ciPaiName) {
            case "清平乐":
                map.put("numOfWords", 46);
                map.put("numOfSentences", 8);
                map.put("1", 4);
                map.put("2", 5);
                map.put("3", 7);
                map.put("4", 6);
                map.put("5", 6);
                map.put("6", 6);
                map.put("7", 6);
                map.put("8", 6);
                break;
            case "浣溪沙":
                map.put("numOfWords", 42);
                map.put("numOfSentences", 6);
                map.put("1", 7);
                map.put("2", 7);
                map.put("3", 7);
                map.put("4", 7);
                map.put("5", 7);
                map.put("6", 7);
                break;
            case "菩萨蛮":
                map.put("numOfWords", 44);
                map.put("numOfSentences", 8);
                map.put("1", 7);
                map.put("2", 7);
                map.put("3", 5);
                map.put("4", 5);
                map.put("5", 5);
                map.put("6", 5);
                map.put("7", 5);
                map.put("8", 5);
                break;
        }
        return map;
    }

}
