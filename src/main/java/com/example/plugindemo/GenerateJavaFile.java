package com.example.plugindemo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.LocalFileSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateJavaFile extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // 创建一个 JFrame 作为弹窗
        JFrame frame = new JFrame("生成代码文件");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        // 创建输入框
        JTextArea textArea = new JTextArea();
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // 创建底部面板
        JPanel bottomPanel = new JPanel(new BorderLayout());
        // 创建类名标签和输入框
        JLabel classNameLabel = new JLabel("输入文件名:");
        // 输入文件名称的输入框
        JTextField classNameField = new JTextField(15);

        // 创建转换按钮
        JButton convertButton = new JButton("开始生成");

        // 将组件添加到底部面板
        bottomPanel.add(classNameLabel, BorderLayout.WEST);
        bottomPanel.add(classNameField, BorderLayout.CENTER);
        bottomPanel.add(convertButton, BorderLayout.EAST);

        // 将底部面板添加到主面板
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // 将主面板添加到窗口
        frame.add(panel);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputContent = textArea.getText();
                String fileName = classNameField.getText();

                saveCodeFile(anActionEvent, fileName, inputContent);
            }
        });


        frame.setSize(800, 600);
        frame.setVisible(true);
    }


    /**
     * 将生成的代码写入到文件中并保存
     *
     * @param event
     * @param fileName
     * @param content
     */
    private void saveCodeFile(AnActionEvent event, String fileName, String content) {
        // 获取当前右键点击的文件夹
        File clickedFolder = new File(event.getDataContext().getData("virtualFile").toString());
        System.out.print("saveCodeFile clickedFolder path=" + clickedFolder.getPath() + ", separator=" + File.separator);
        String filePath = clickedFolder.getPath() + File.separator + fileName + ".java";
        //去掉路径的前缀
        filePath = filePath.replace("file:", "");
        System.out.print("saveCodeFile filePath=" + filePath);
        content = "class "+fileName+"{\n"+content+"\n}";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 刷新目录，以便 IntelliJ IDEA 可以显示新创建的文件
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(clickedFolder.getParentFile());

    }
}
