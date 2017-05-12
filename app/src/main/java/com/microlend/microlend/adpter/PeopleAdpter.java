package com.microlend.microlend.adpter;

/**
 * Created by young on 2017/5/12.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microlend.microlend.R;
import com.microlend.microlend.model.Lend;

import org.litepal.crud.DataSupport;

import java.util.List;


public class PeopleAdpter extends RecyclerView.Adapter<PeopleAdpter.ViewHolder> {

    private static final String TAG = "LendAdpter";
    private Context mContext;
    private List<Lend> mLendList;

    public PeopleAdpter(List<Lend> lendList) {
        mLendList = lendList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View lendItemVIew;
        TextView lendName;
        TextView lendTel;
        TextView lendID;

        public ViewHolder(View itemView) {
            super(itemView);
            lendItemVIew = itemView;
            lendName = (TextView) itemView.findViewById(R.id.tv_peoplename_lendpeople);
            lendTel = (TextView) itemView.findViewById(R.id.tv_peopletel_lendpeople);
            lendID = (TextView) itemView.findViewById(R.id.tv_peopleid_lendpeople);
        }
    }

    @Override
    public PeopleAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.people_item, parent, false);
        final ViewHolder peopleHolder = new ViewHolder(view);

        peopleHolder.lendItemVIew.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                dialog.setTitle("删除");
                dialog.setMessage("您确定要删除?会把该联系人的贷款信息一同删除");
                dialog.setCancelable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = peopleHolder.getAdapterPosition();
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
        return peopleHolder;
    }

    @Override
    public void onBindViewHolder(PeopleAdpter.ViewHolder holder, int position) {
        Lend lend = mLendList.get(position);
        holder.lendName.setText(lend.getLoadPeopleName());
        holder.lendID.setText(lend.getLoadPeopleIDCard());
        holder.lendTel.setText(lend.getTelPhone());
    }

    @Override
    public int getItemCount() {
        return mLendList.size();
    }
}


