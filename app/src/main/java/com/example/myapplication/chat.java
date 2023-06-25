package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class chat extends Fragment {

    EditText etip,etreceiveport,etzhenceport,etmess;
    Button btnok,btnsend;
    TextView tvshow;
    // 定义网络相关变量
    InetAddress inetaddress = null;
    DatagramPacket pack;
    DatagramSocket sendsocket=null,receivesocket=null;
    Message m;
    String ip;
    int receiveport,zhenceport;

    // 定义消息类型常量
    static final int RECEIVE_WHAT=1,SEND_WHAT=2;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);

        Button btnok = (Button) view.findViewById(R.id.btn_ok);
        Button btnsend = (Button) view.findViewById(R.id.btn_send);

        // 绑定id
        etip = view.findViewById(R.id.et_ip);
        etreceiveport = view.findViewById(R.id.et_port);
        etzhenceport = view.findViewById(R.id.et_zhenceport);
        etmess = view.findViewById(R.id.et_mess);
        tvshow = view.findViewById(R.id.tv_show);

        // 设置按钮点击事件
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = etip.getText().toString();
                receiveport = Integer.parseInt(etreceiveport.getText().toString());
                zhenceport = Integer.parseInt(etzhenceport.getText().toString());
                // Start receiving message thread
                new Thread(new ReceiveMessage()).start();
            }
        });
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new SendMessage()).start();
            }
        });

        // Add scrolling functionality
        TextView tvshow = view.findViewById(R.id.tv_show);
        tvshow.setMovementMethod(ScrollingMovementMethod.getInstance());
        return view;
    }
//  // 初始化视图
//  public void initView()
//  {
//    etip = getActivity().findViewById(R.id.et_ip);
//    etreceiveport = getActivity().findViewById(R.id.et_port);
//    etzhenceport = getActivity().findViewById(R.id.et_zhenceport);
//    etmess = getActivity().findViewById(R.id.et_mess);
//    btnok = getActivity().findViewById(R.id.btn_ok);
//    btnsend = getActivity().findViewById(R.id.btn_send);
//    tvshow = getActivity().findViewById(R.id.tv_show);
//  }

    // 发送消息类
    public class SendMessage implements Runnable{

        @Override
        public void run() {
            // 获取要发送的消息
            String   sendmessage = etmess.getText().toString();

            // 将消息转换为字节数组
            byte[] buffer = sendmessage.getBytes();
            try {
                // 获取目标IP地址
                inetaddress = InetAddress.getByName(ip);
                // 创建数据包
                pack = new DatagramPacket(buffer,buffer.length,inetaddress,receiveport);
                // 创建发送套接字
                sendsocket = new DatagramSocket();
                // 发送数据包
                sendsocket.send(pack);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (SocketException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // 线程休眠500毫秒
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 将发送的消息封装到Message对象中
            Bundle bundle = new Bundle();
            bundle.putString("send",sendmessage);
            m = new Message();
            m.what = SEND_WHAT;
            m.setData(bundle);

            // 将Message对象发送给handler处理
            handler.sendMessage(m);
            // 关闭发送套接字
            sendsocket.close();

        }
    }
    // 接收消息类
    public class ReceiveMessage implements Runnable{
        // 定义接收数据的缓冲区和消息变量
        byte data[] = new byte[8192];
        String message = null,str;

        @Override
        public void run() {
            // 创建接收数据包
            pack = new DatagramPacket(data,data.length);
            try {
                // 创建接收套接字
                receivesocket = new DatagramSocket(null);
                receivesocket.setReuseAddress(true);
                receivesocket.bind(new InetSocketAddress(zhenceport));
            } catch (SocketException e) {
                e.printStackTrace();
            }
            // 循环接收消息
            do
            {
                try {
                    // 接收数据包
                    receivesocket.receive(pack);
                    // 解析数据包内容
                    message = new String(pack.getData(),0,pack.getLength());
                    // 获取发送方IP地址
                    str = pack.getAddress().getHostAddress();
                    // 将接收到的消息封装到Message对象中
                    m = new Message();
                    m.what=RECEIVE_WHAT;

                    Bundle bundle = new Bundle();
                    bundle.putString("receiveip",str);
                    bundle.putString("receivedata",message);
                    m.setData(bundle);
                    // 线程休眠500毫秒
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 将Message对象发送给handler处理
                    handler.sendMessage(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }while(!"quit".equalsIgnoreCase(message));
            // 关闭接收套接字
            receivesocket.close();
        }
    }
    // 处理消息
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 根据消息类型进行处理
            switch(msg.what){
                case RECEIVE_WHAT:
                    // 显示接收到的消息
                    tvshow.append("收到"+msg.getData().getString("receiveip")+"的消息："+
                            msg.getData().getString("receivedata")+"\n");
                    break;
                case SEND_WHAT:
                    // 显示发送的消息
                    tvshow.append("发送的消息："+msg.getData().getString("send").toString()+"\n");
                    //etmess.setText("");
                    break;
            }
            // 滚动到底部
            tvshow.post(new Runnable() {
                @Override
                public void run() {
                    int scrollAmount = tvshow.getLayout().getLineTop(tvshow.getLineCount()) - tvshow.getHeight();
                    if (scrollAmount > 0) {
                        tvshow.scrollTo(0, scrollAmount);
                    } else {
                        tvshow.scrollTo(0, 0);
                    }
                }
            });
        }
    };
}
