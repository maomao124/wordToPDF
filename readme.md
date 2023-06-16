# 概述
**此项目是用于将docx或者doc文件批量转换成pdf文件，支持遍历子目录**

# 环境要求
* jdk8
* 电脑上存在office办公软件

# 使用方法

运行jar包
```shell
java -jar wordToPDF.jar
```

意思是将工作目录（一般是与jar包同级的目录）下的所有doc或者docx文件转换成pdf文件



# 参数

指定要转换的word文件名：
```shell
java -jar wordToPDF.jar --path.wordFileName=in.docx
```
可以在文件名前面添加路径

<br><br>

指定要转换的word文件名和输出的文件名：
```shell
java -jar wordToPDF.jar --path.wordFileName=in.docx --path.pdfFileName=out.pdf
```

<br><br>

指定要转换的目录：
```shell
java -jar wordToPDF.jar --path.inputPath=C:\Users\mao\Desktop\ 
```

> 注意：目录必须已 \ 或者 / 结尾

<br><br>

指定要转换的目录和子目录：
```shell
java -jar wordToPDF.jar --path.inputPath=C:\Users\mao\Desktop\ --path.subdirectory=true
```

<br><br>

指定要转换的目录，并指定要输出的目录：
```shell
java -jar wordToPDF.jar --path.inputPath=C:\Users\mao\Desktop\ --path.outputPath=D:\out 
```



<br><br>

yaml配置：
```yaml
path:
  inputPath: C:\Users\mao\Desktop\
  outputPath: C:\Users\mao\Desktop\img\
  wordFileName: C:\Users\mao\Desktop\out4.docx
  pdfFileName: C:\Users\mao\Desktop\img\out5.pdf
  subdirectory: true
```


<br><br>

> 如果觉得程序启动不方便，可以使用exe4j将jar包变成exe文件，双击运行
