有暇，做了个五子棋大战的小游戏送给各位小伙伴！  

用到的知识点有：  
>1.JavaWeb基础知识（懂jsp，servlet足够）  
>2.JavaScript和jQuery基本用法  
>3.了解WebSocket的基本用法  

OK，关于第三点，如果小伙伴们之前没有接触过WebSocket，或者对WebSocket并不太熟悉，可以先阅读这三篇文章学习下：  

<big>1.[WebSocket刨根问底(一)](http://blog.csdn.net/u012702547/article/details/77621195)  </big>

<big>2.[WebSocket刨根问底(二)](http://blog.csdn.net/u012702547/article/details/77655826)  </big>

<big>3.[WebSocket刨根问底(三)之群聊](http://blog.csdn.net/u012702547/article/details/77671750)</big>

# 游戏效果图

1.登录效果  

![这里写图片描述](http://img.blog.csdn.net/20170830143417788?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjcwMjU0Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

2.游戏进行时效果

![这里写图片描述](http://img.blog.csdn.net/20170830141758200?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjcwMjU0Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)  

3.房间满员后，再进入，会有错误提示，如下：  

![这里写图片描述](http://img.blog.csdn.net/20170830150303549?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjcwMjU0Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


# 核心思路

>1.该游戏本质上就是两个人聊天，只不过聊天的内容为棋盘上的坐标  
>2.用户点击棋盘，根据点击的位置，算出离点击位置最近的一个**十**字的坐标作为此次点击事件的坐标，将之传递给WebSocket服务端  
>3.当有两个人已经连接上服务端的时候，此时如果有第三个人连接服务端，则服务端返回一条消息告诉该客户端房间人已满，并且让该客户端主动关闭连接  
>4.每下一子，都去判断一下该子的**上\下，左\右，左上\右下，左下\右上**四个方向上同类型的子是否已满5个，满5个表示此次落子的用户赢  
>5.游戏进行过程中，有一方关闭浏览器，会提示另一方某某人已经下线  


**源码地址：[https://github.com/lenve/GobangGame](https://github.com/lenve/GobangGame)**

**博客地址：[WebSocket刨根问底(四)之五子棋大战江湖](http://blog.csdn.net/u012702547/article/details/77715369)**

欢迎小伙伴们fork，star。

更多JavaEE资料请关注公众号：

![这里写图片描述](http://img.blog.csdn.net/20170830152727931?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjcwMjU0Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

以上。



