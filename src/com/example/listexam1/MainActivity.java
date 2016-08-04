package com.example.listexam1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    SQLiteDatabase db;
    boolean find=false;
    LinearLayout linearyout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line);
		db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/test.db3", null);
		String createsql="create table h1(_id integer primary key autoincrement,c1,c2)";
		String searchsql="select * from sqlite_master where name='h1'";
		Cursor cursor=db.rawQuery(searchsql, null);
		if(cursor.getCount()==0)
			db.execSQL(createsql);
	}
	public void handleOnclick(View view){
		EditText etName =(EditText)findViewById(R.id.etname);
		String name=etName.getText().toString();
		String searchsql="select * from h1 where c1='"+name+"'";
		Cursor cursor=db.rawQuery(searchsql, null);
		find=false;
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		
		if(cursor.moveToNext()){
			builder.setTitle("查询结果：");
			find=true;
			String Phonenumber=cursor.getString(2);
			builder.setMessage("单词意思是："+Phonenumber);
			
		}
		else{
		//	builder.setTitle("添加：");
			builder.setTitle("单词不存在！,是否添加到单词本中？");
		 linearyout=(LinearLayout)this.getLayoutInflater().inflate(R.layout.add_info, null);
			builder.setView(linearyout);
			EditText etn=(EditText)linearyout.findViewById(R.id.etn);
			etn.setText(name);
			builder.setPositiveButton("确定", new L1());
			builder.setNegativeButton("取消", new L2());
			
		}
		builder.create();
		builder.show();
	}
	class L1 implements DialogInterface.OnClickListener{
		public void onClick(DialogInterface dialog,int which){
			EditText etn=(EditText)linearyout.findViewById(R.id.etn);
			EditText etp=(EditText)linearyout.findViewById(R.id.etp);
			String name=etn.getText().toString();
			String phone=etp.getText().toString();
			String insertsql="insert into h1 values(null,?,?)";
			db.execSQL(insertsql,new String[]{name,phone});
			Toast.makeText(MainActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
			
		}
	}
	class L2 implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	}
}
