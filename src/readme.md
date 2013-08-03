1.android 为了适应不同屏幕的大小 分别有hdpi l m，android会自动的去加载相应的资源文件，所以在制作的时候可以制作几个不同版本的图片即可。
2.android每次升级的时候都会自动去更改某个文件路径修改一下，做一个软链接 ls -s from->target