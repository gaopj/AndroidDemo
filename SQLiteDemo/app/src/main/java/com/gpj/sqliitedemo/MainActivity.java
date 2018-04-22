package com.gpj.sqliitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";

    private OrderDao ordersDao;

    private TextView showSQLMsg;

    private EditText inputSqlMsg;

    private ListView showDateListView;

    private List<Order> orderList;

    private OrderListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ordersDao = new OrderDao(this);
        if (! ordersDao.isDataExist()){
            ordersDao.initTable();
        }

        initView();

        orderList = ordersDao.getAllDate();
        if (orderList != null){
            adapter = new OrderListAdapter(this, orderList);
            showDateListView.setAdapter(adapter);
        }
    }

    private void initView(){
        Button executeButton = findViewById(R.id.executeButton);
        Button insertButton = findViewById(R.id.insertButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button query1Button = findViewById(R.id.query1Button);
        Button query2Button = findViewById(R.id.query2Button);
        Button query3Button = findViewById(R.id.query3Button);


        SQLBtnOnclickListener onclickListener = new SQLBtnOnclickListener();
        executeButton.setOnClickListener(onclickListener);
        insertButton.setOnClickListener(onclickListener);
        deleteButton.setOnClickListener(onclickListener);
        updateButton.setOnClickListener(onclickListener);
        query1Button.setOnClickListener(onclickListener);
        query2Button.setOnClickListener(onclickListener);
        query3Button.setOnClickListener(onclickListener);

        inputSqlMsg = findViewById(R.id.inputSqlMsg);
        showSQLMsg = findViewById(R.id.showSQLMsg);
        showDateListView = findViewById(R.id.showDateListView);
        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.show_sql_item, null), null, false);
    }

    private void refreshOrderList(){
        // 注意：千万不要直接赋值，如：orderList = ordersDao.getAllDate() 此时相当于重新分配了一个内存 原先的内存没改变 所以界面不会有变化
        // Java中的类是地址传递 基本数据才是值传递
        orderList.clear();
        orderList.addAll(ordersDao.getAllDate());
        adapter.notifyDataSetChanged();
    }

    public class SQLBtnOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.executeButton:
                    showSQLMsg.setVisibility(View.GONE);
                    String sql = inputSqlMsg.getText().toString();
                    if (! TextUtils.isEmpty(sql)){
                        ordersDao.execSQL(sql);
                    }else {
                        Toast.makeText(MainActivity.this, R.string.strInputSql, Toast.LENGTH_SHORT).show();
                    }

                    refreshOrderList();
                    break;

                case R.id.insertButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("新增一条数据：\n添加数据(7, \"Jne\", 700, \"China\")\ninsert into Orders(Id, CustomName, OrderPrice, Country) values (7, \"Jne\", 700, \"China\")");
                    ordersDao.insertDate();
                    refreshOrderList();
                    break;

                case R.id.deleteButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("删除一条数据：\n删除Id为7的数据\ndelete from Orders where Id = 7");
                    ordersDao.deleteOrder();
                    refreshOrderList();
                    break;

                case R.id.updateButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("修改一条数据：\n将Id为6的数据的OrderPrice修改了800\nupdate Orders set OrderPrice = 800 where Id = 6");
                    ordersDao.updateOrder();
                    refreshOrderList();
                    break;

                case R.id.query1Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    StringBuilder msg = new StringBuilder();
                    msg.append("数据查询：\n此处将用户名为\"Bor\"的信息提取出来\nselect * from Orders where CustomName = 'Bor'");
                    List<Order> borOrders = ordersDao.getBorOrder();
                    for (Order order : borOrders){
                        msg.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                    }
                    showSQLMsg.setText(msg);
                    break;

                case R.id.query2Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    int chinaCount = ordersDao.getChinaCount();
                    showSQLMsg.setText("统计查询：\n此处查询Country为China的用户总数\nselect count(Id) from Orders where Country = 'China'\ncount = " + chinaCount);
                    break;

                case R.id.query3Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    StringBuilder msg2 = new StringBuilder();
                    msg2.append("比较查询：\n此处查询单笔数据中OrderPrice最高的\nselect Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders");
                    Order order = ordersDao.getMaxOrderPrice();
                    msg2.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                    showSQLMsg.setText(msg2);
                    break;

                default:

                    break;
            }
        }
    }
}
