package com.im.chat.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.im.chat.event.GroupItemClickEvent;
import com.im.chat.util.ConversationUtils;

import cn.leancloud.chatkit.view.RoundImageView;
import cn.leancloud.chatkit.viewholder.LCIMCommonViewHolder;
import de.greenrobot.event.EventBus;

/**
 * 群聊item的holder
 * Created by cjxiao on 15/11/27.
 */
public class GroupItemHolder extends LCIMCommonViewHolder<AVIMConversation> {

  private RoundImageView iconView;
  private TextView nameView;
  private AVIMConversation conversation;

  public GroupItemHolder(Context context, ViewGroup root) {
    super(context, root, com.im.chat.R.layout.group_item_layout);

    iconView = (RoundImageView) itemView.findViewById(com.im.chat.R.id.group_item_iv_icon);
    nameView = (TextView) itemView.findViewById(com.im.chat.R.id.group_item_tv_name);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != conversation) {
          EventBus.getDefault().post(new GroupItemClickEvent(conversation.getConversationId()));
        }
      }
    });
  }

  @Override
  public void bindData(AVIMConversation conversation) {
    this.conversation = conversation;
    iconView.setImageResource(com.im.chat.R.drawable.contact_group_icon);
    if (null != conversation) {
      ConversationUtils.getConversationName(conversation, new AVCallback<String>() {
        @Override
        protected void internalDone0(String s, AVException e) {
          nameView.setText(s+"("+conversation.getMembers().size()+")");
        }
      });
    } else {
      nameView.setText("");
    }
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<GroupItemHolder>() {
    @Override
    public GroupItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new GroupItemHolder(parent.getContext(), parent);
    }
  };
}
