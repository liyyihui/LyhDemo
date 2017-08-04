
TextView点击展开  和部分变色



  依赖方式
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
Copy

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.liyyihui:LyhDemo:2.0'
	}
   点击展开控件
 <com.example.mymis.lyhview.InfoTextView
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      app:showend="20"   显示字符串的长度
      app:mtext="点击展开"  默认是  点击展开
      app:mhidetext="点击收缩"   默认是点击收缩
      app:mtextcolor="#f90000"   点击文字的颜色
      android:text="    anchor the view on wh
          />
      
       部分文字变色  
       <com.example.mymis.lyhview.LYHTextview
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:changecolor="#ff0202"  变的颜色
      app:start="2" //开始位置
      app:end="4"   //结束位置
      android:text="文本的信息"
      />
