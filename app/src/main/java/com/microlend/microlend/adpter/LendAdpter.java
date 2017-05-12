package com.microlend.microlend.adpter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.microlend.microlend.R;
import com.microlend.microlend.activity.LendContentActivity;
import com.microlend.microlend.activity.MainActivity;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;

import java.util.List;


/**
 * Created by young on 2017/5/11.
 */

public class LendAdpter extends RecyclerView.Adapter<LendAdpter.ViewHolder> {

    private static final String TAG = "LendAdpter";
    private Context mContext;
    private List<Lend> mLendList;

    public LendAdpter(List<Lend> lendList) {
        mLendList = lendList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View lendItemVIew;
        TextView lendName;
        TextView money;
        TextView tel;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            lendItemVIew = itemView;
            lendName = (TextView) itemView.findViewById(R.id.tv_lendName_lenditem);
            money = (TextView) itemView.findViewById(R.id.tv_lendmoney_lenditem);
            tel = (TextView) itemView.findViewById(R.id.tv_tel_lenditem);
            time = (TextView) itemView.findViewById(R.id.tv_time_lenditem);

        }
    }

    @Override
    public LendAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.lend_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.lendItemVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context=this;
                int position = holder.getAdapterPosition();
                Lend lend = mLendList.get(position);
                Intent intent = new Intent(mContext, LendContentActivity.class);
                Log.w(TAG, "onClick: id " + lend.getId());
                intent.putExtra("id", lend.getId());
                mContext.startActivity(intent);
                Toast.makeText(view.getContext(), lend.getLoadPeopleName() + "", Toast.LENGTH_SHORT).show();
            }
        });

        holder.lendItemVIew.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                dialog.setTitle("删除");
                dialog.setMessage("您确定要删除?");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getAdapterPosition();
                        Lend lend = mLendList.get(position);
                        //删除
                        DataSupport.deleteAll(Lend.class, "id=" + lend.getId());
                    }
                });
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(LendAdpter.ViewHolder holder, int position) {
        Lend lend = mLendList.get(position);
        holder.lendName.setText("借贷人姓名：" + lend.getLoadPeopleName());
        String date = lend.getYear() + "/" + lend.getMonth() + "/" + lend.getDay();
        holder.time.setText("预计还款日期：" + date);
        holder.tel.setText("电话号码：" + lend.getTelPhone());
        Log.w(TAG, "onBindViewHolder: " + lend.getSumMoney());
        String sum = String.valueOf(lend.getSumMoney());
        Log.w(TAG, "onBindViewHolder: sum " + sum);
        holder.money.setText("总金额：" + sum);

    }

    @Override
    public int getItemCount() {
        return mLendList.size();
    }
}

