package mao.wordtopdf.service.impl;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import mao.wordtopdf.config.wordConfigProp;
import mao.wordtopdf.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Project name(项目名称)：wordToPDF
 * Package(包名): mao.wordtopdf.service.impl
 * Class(类名): WordServiceImpl
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/6/16
 * Time(创建时间)： 13:45
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@Service
public class WordServiceImpl implements WordService
{
    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(WordServiceImpl.class);


    @Resource
    private wordConfigProp wordConfigProp;

    /**
     * 一个文件名，doc或者docx格式，将文件名的后缀名改为pdf
     *
     * @param wordFileName 字文件名称
     * @return {@link String} pdf后缀名的文件名
     */
    private String fileNameWordToPdf(String wordFileName)
    {
        return wordFileName.substring(0, wordFileName.lastIndexOf(".")) + ".pdf";
    }

    @Override
    public void toPDF(String wordFileName, String pdfFileName)
    {
        if (wordFileName == null)
        {
            throw new IllegalArgumentException("请输入正确的文件名");
        }
        if (!(wordFileName.endsWith(".docx") || wordFileName.endsWith(".doc")))
        {
            throw new IllegalArgumentException("文件的后缀名不正确");
        }
        if (pdfFileName == null)
        {
            pdfFileName = fileNameWordToPdf(wordFileName);
        }
        else
        {
            if (!pdfFileName.endsWith(".pdf"))
            {
                throw new IllegalArgumentException("文件的后缀名不正确");
            }
        }

        //注意：这里调用了动态链接库，工作路径已经不是查询当前路径了，使用相对路径可能会出现找不到的问题
        ActiveXComponent app = null;
        try
        {
            log.debug(wordFileName + " , " + pdfFileName);
            //调用window中的程序
            app = new ActiveXComponent("Word.Application");
            //调用的时候不显示窗口
            app.setProperty("Visible", false);
            //获得所有打开的文档
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc;
            if (wordConfigProp.getWordFileName() == null)
            {
                if (wordConfigProp.isSubdirectory())
                {
                    doc = Dispatch.call(docs, "Open", new File(wordFileName).getAbsolutePath()).toDispatch();
                    log.info(wordFileName + " ---> " + pdfFileName);
                    log.info("输出路径：" + new File(pdfFileName).getAbsolutePath());
                    //另存为，将文档保存为pdf，其中Word保存为pdf的格式宏的值是17
                    Dispatch.call(doc, "SaveAs", new File(pdfFileName).getAbsolutePath(), 17);
                    Dispatch.call(doc, "Close");
                    log.info(wordFileName + " 完成");
                    return;
                }
                else
                {
                    doc = Dispatch.call(docs, "Open", new File(wordConfigProp.getInputPath() + wordFileName).getAbsolutePath()).toDispatch();
                }
            }
            else
            {
                doc = Dispatch.call(docs, "Open", new File(wordFileName).getAbsolutePath()).toDispatch();
            }
            log.info(wordFileName + " ---> " + pdfFileName);
            log.info("输出路径：" + new File(pdfFileName).getAbsolutePath());
            //另存为，将文档保存为pdf，其中Word保存为pdf的格式宏的值是17
            Dispatch.call(doc, "SaveAs", new File(pdfFileName).getAbsolutePath(), 17);
            Dispatch.call(doc, "Close");
            log.info(wordFileName + " 完成");
        }
        catch (Exception e)
        {
            //Toolkit.getDefaultToolkit().beep();
            e.printStackTrace();
            log.error("转pdf时发生错误：", e);
        }
        finally
        {
            //关闭office
            if (app != null)
            {
                app.invoke("Quit", 0);
            }
        }
    }

    @Override
    public void toPDF(String wordFileName)
    {
        toPDF(wordFileName, null);
    }

    @Override
    public void checkLib()
    {
        String[] names = new String[]{"jacob-1.20-x64.dll", "jacob-1.20-x86.dll"};

        for (String name : names)
        {
            File path = new File("./" + name);
            if (!path.exists())
            {
                log.warn("库文件\"" + name + "\"不存在！ 将自动生成");
                InputStream inputStream = WordServiceImpl.class.getClassLoader().getResourceAsStream(name);
                try (FileOutputStream fileOutputStream = new FileOutputStream("./" + name))
                {
                    FileCopyUtils.copy(inputStream, fileOutputStream);
                }
                catch (Exception e)
                {
                    log.warn("库文件\"" + name + "\"写入失败", e);
                }
            }
        }
    }

    @Override
    public void exec() throws IOException
    {
        log.info(wordConfigProp.toString());
        if (wordConfigProp.getWordFileName() != null && wordConfigProp.getPdfFileName() != null)
        {
            this.toPDF(wordConfigProp.getWordFileName(), wordConfigProp.getPdfFileName());
            return;
        }
        if (wordConfigProp.getWordFileName() != null)
        {
            this.toPDF(wordConfigProp.getWordFileName());
            return;
        }
        List<String> wordFileList = new ArrayList<>();
        if (wordConfigProp.isSubdirectory())
        {
            log.info("正在遍历子目录...");
            Files.walkFileTree(Paths.get(wordConfigProp.getInputPath()), new FileVisitor<Path>()
            {
                /**
                 * 访问目录之前的回调方法
                 *
                 * @param dir   dir
                 * @param attrs attrs
                 * @return {@link FileVisitResult}
                 * @throws IOException ioexception
                 */
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
                {
                    return FileVisitResult.CONTINUE;
                }

                /**
                 * 访问文件
                 *
                 * @param file  文件
                 * @param attrs attrs
                 * @return {@link FileVisitResult}
                 * @throws IOException ioexception
                 */
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                {
                    File file1 = file.toFile();
                    if (file1.getName().endsWith(".doc") || file1.getName().endsWith(".docx"))
                    {
                        wordFileList.add(file1.getAbsolutePath());
                    }
                    return FileVisitResult.CONTINUE;
                }

                /**
                 * 访问文件失败
                 *
                 * @param file 文件
                 * @param exc  exc
                 * @return {@link FileVisitResult}
                 * @throws IOException ioexception
                 */
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
                {
                    log.warn("文件 " + file + " 无法访问 : " + exc.getMessage());
                    return FileVisitResult.CONTINUE;
                }

                /**
                 * 访问目录之后的回调方法
                 *
                 * @param dir dir
                 * @param exc exc
                 * @return {@link FileVisitResult}
                 * @throws IOException ioexception
                 */
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
                {
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        else
        {
            File file = new File(wordConfigProp.getInputPath());
            File[] files = file.listFiles();
            for (File file1 : files)
            {
                if (file1.getName().endsWith(".doc") || file1.getName().endsWith(".docx"))
                {
                    wordFileList.add(file1.getName());
                }
            }
        }
        log.info("一共" + wordFileList.size() + "个任务");
        if (wordConfigProp.isSubdirectory())
        {
            for (String name : wordFileList)
            {
                this.toPDF(name, fileNameWordToPdf(name));
            }
        }
        else
        {
            if (wordConfigProp.getOutputPath() == null)
            {
                for (String name : wordFileList)
                {
                    this.toPDF(name, wordConfigProp.getInputPath() + fileNameWordToPdf(name));
                }
            }
            else
            {
                for (String name : wordFileList)
                {
                    this.toPDF(name, wordConfigProp.getOutputPath() + fileNameWordToPdf(name));
                }
            }
        }
        log.info("任务全部完成");
    }
}
