# SlidingDeleteView
Android简单实现滑动删除Item

效果如图：
![image](https://github.com/yanjunhui2014/SlidingDeleteView/tree/master/gif/slidingdelete.gif)

需求：
要做出类似QQ左滑删除的效果。

分析：
Android中什么组件自带了水平滑动？ 我首当其冲就想到HorizontalScrollView。既然是简单实现，那我们何方不继承一下HorizontalScrollView呢？

编程思想：
接下来我们将HorizontalScrollView分成ItemView + 抽屉部分。

我们只需要监听用户的滑动事件，当用户滑动超过指定距离时，就移动HorizontalScrollView，将隐藏的“抽屉部分”显示出来，当用户做相反的滑动时，隐藏“抽屉部分”即可。
