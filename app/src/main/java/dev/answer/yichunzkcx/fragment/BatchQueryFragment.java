package dev.answer.yichunzkcx.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.bean.GradeResponse;
import dev.answer.yichunzkcx.databinding.FragmentBatchqueryBinding;
import dev.answer.yichunzkcx.network.YiChunZkApi;
import dev.answer.yichunzkcx.util.ExcelQueryTool;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BatchQueryFragment extends BaseFragment {

  private FragmentBatchqueryBinding binding;

  private int defaultColor;
  private YiChunZkApi util;
  private ExcelQueryTool excelTool;

  private int count = 0;
  private int name_site;
  private int number_site;

  private ArrayList<String> nameList;
  private ArrayList<String> numberList;
  private ArrayList<GradeResponse.Data> gradeList;

  @Override
  public View loadRootView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // TODO: Implement this method
    binding = FragmentBatchqueryBinding.inflate(inflater, container, false);
    View parentView = binding.getRoot();
        initBinding(binding);
    try {

      initBar();
      defaultColor = binding.cardInfo.getCardBackgroundColor().getDefaultColor();

      MaterialButton query_button = findViewById(R.id.query_button);
      MaterialButton read_button = findViewById(R.id.read_button);
      MaterialButton output_button = findViewById(R.id.output_button);

      binding.codeImage.setOnClickListener(view -> renewed());
      query_button.setOnClickListener(view -> query());
      read_button.setOnClickListener(view -> read());
      output_button.setOnClickListener(view -> output());

      // init util
      util = new YiChunZkApi(getActivity());
      util.setImageView(binding.codeImage);
      util.QueryCode();

      excelTool = new ExcelQueryTool();

      // init list
      nameList = new ArrayList<>();
      numberList = new ArrayList<>();
      gradeList = new ArrayList<>();

    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }

    return parentView;
  }

  public void read() {
    try {
      initData();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  public void renewed() {
    try {
      util.QueryCode();
    } catch (Throwable error) {
      error.printStackTrace();
      toast(error.toString());
    }
  }

  private void initData() throws Exception {
    // init Apache POI
    excelTool.CreateByPath(removeSpaces(binding.inputTextInput));
    for (int s = 0; excelTool.getSheetSize() > s; s++) {
      // 基本信息位置获取
      Sheet sheet = excelTool.getSheet(s);
      Row headerRow = sheet.getRow(1); // 获取标题行
      int examNumberIndex = -1; // 准考证号所在的列索引
      int nameIndex = -1; // 姓名所在的列索引

      // 遍历标题行的每个单元格，查找准考证号和姓名所在的列

      for (Cell cell : headerRow) {
        String cellValue = cell != null ? cell.getStringCellValue() : "";

        if (cellValue.contains("准考证号")) {
          examNumberIndex = cell.getColumnIndex();
        } else if (cellValue.contains("姓名")) {
          nameIndex = cell.getColumnIndex();
        }

        // 如果已经找到准考证号和姓名所在的列，则跳出循环
        if (examNumberIndex != -1 && nameIndex != -1) {
          break;
        }
      }

      // 遍历每一行（从第二行开始，跳过标题行）
      for (int i = 2; i <= sheet.getLastRowNum(); i++) {

        Row row = sheet.getRow(i);
        if (row == null) continue;
        // 读取准考证号和姓名
        Cell examNumberCell = row.getCell(examNumberIndex);
        Cell nameCell = row.getCell(nameIndex);

        String examNumber = examNumberCell != null ? examNumberCell.getStringCellValue() : "";
        String name = nameCell != null ? nameCell.getStringCellValue() : "";

        // 判断空行直接跳过
        if (examNumber.isEmpty() && name.isEmpty()) {
          continue;
        }

        // 判断准考证号为空的话，不进行请求验证
        if (examNumber.isEmpty()) {
          // 创建 GradeResponse 对象
          GradeResponse gradeResponse = new GradeResponse();
          // 设置 GradeResponse 对象的属性值
          gradeResponse.setCode(200);
          gradeResponse.setMsg("Success");
          // 创建 Data 对象
          GradeResponse.Data data = gradeResponse.getData();
          data.setXm1(name);
          data.setZkzh("");
          gradeList.add(data);
        } else {
          // add list
          nameList.add(name);
          numberList.add(examNumber);
        }
      }
    }
    excelTool.close();

    if (nameList != null && numberList != null) {
      binding.infoName.setText("学生姓名: " + nameList.get(count));
      binding.infoNumber.setText("准考证号: " + numberList.get(count));
      binding.infoCount.setText("剩余: " + (nameList.size() - count));
      toast("读取成功");
    } else {
      toast("读取失败或是Excel表格为空");
    }
  }

  public void query() {
    try {
      if (TextUtils.isEmpty(binding.codeTextInput.getText().toString())) {
        binding.codeTextInput.setError("请输入验证码");
        toast("请输入验证码");
      } else if (!nameList.isEmpty() && !numberList.isEmpty()) {
        String code = removeSpaces(binding.codeTextInput);

        util.setUpDataRunnable(
            () -> {
              gradeList.add(util.getGradeResponse().getData());
              binding.codeTextInput.setText("");
              binding.cardInfo.setCardBackgroundColor(Color.parseColor("#4CAF50"));
              delayed(
                  () -> {
                    binding.cardInfo.setCardBackgroundColor(defaultColor);
                  },
                  1800);
              toast("查询成功，正在刷新验证码");
              count++;
              util.QueryCode();
            });

        util.setFailRunnable(
            () -> {
              binding.codeTextInput.setText("");
              binding.cardInfo.setCardBackgroundColor(Color.parseColor("#F44336"));
              delayed(
                  () -> {
                    binding.cardInfo.setCardBackgroundColor(defaultColor);
                  },
                  1800);
              util.QueryCode();
            });

        binding.infoName.setText("学生姓名: " + nameList.get(count));
        binding.infoNumber.setText("准考证号: " + numberList.get(count));
        binding.infoCount.setText("剩余: " + (nameList.size() - count));

        // 判断准考证号为空的话，不进行请求验证
        if (!numberList.get(count).isEmpty()) {
          util.QueryGrade(nameList.get(count), numberList.get(count), code);
        } else {
          // 创建 GradeResponse 对象
          GradeResponse gradeResponse = new GradeResponse();

          // 设置 GradeResponse 对象的属性值
          gradeResponse.setCode(200);
          gradeResponse.setMsg("Success");

          // 创建 Data 对象
          GradeResponse.Data data = gradeResponse.getData();
          data.setXm1(nameList.get(count));
          data.setZkzh(numberList.get(count));
          gradeList.add(data);
        }
      } else {
        toast("数据可能未初始化，请点击读取");
      }
    } catch (Throwable error) {
      error.printStackTrace();
      toast("Excel版本可能有问题");
    }
  }

  public void output() {
    try {
      if (!gradeList.isEmpty()) {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet("中考成绩单");

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("姓名");
        headerRow.createCell(1).setCellValue("准考证");
        headerRow.createCell(2).setCellValue("语文");
        headerRow.createCell(3).setCellValue("数学");
        headerRow.createCell(4).setCellValue("英语");
        headerRow.createCell(5).setCellValue("物理");
        headerRow.createCell(6).setCellValue("化学");
        headerRow.createCell(7).setCellValue("政史");
        headerRow.createCell(8).setCellValue("生地");
        headerRow.createCell(9).setCellValue("加分");
        headerRow.createCell(10).setCellValue("实验操作");
        headerRow.createCell(11).setCellValue("体育");
        headerRow.createCell(12).setCellValue("总分");

        // 将数据写入单元格
        List<GradeResponse.Data> dataList = gradeList;

        int rowNum = 1;
        for (GradeResponse.Data data : dataList) {
          if (data.getZkzh().isEmpty() && data.getXm1().isEmpty()) continue;
          if (data.getZkzh().isEmpty() && !data.getXm1().isEmpty()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getXm1());
            row.createCell(1).setCellValue("");
            row.createCell(2).setCellValue("");
            row.createCell(3).setCellValue("");
            row.createCell(4).setCellValue("");
            row.createCell(5).setCellValue("");
            row.createCell(6).setCellValue("");
            row.createCell(7).setCellValue("");
            row.createCell(8).setCellValue("");
            row.createCell(9).setCellValue("");
            row.createCell(10).setCellValue("");
            row.createCell(11).setCellValue("");
            row.createCell(12).setCellValue("");
            continue;
          }

          Row row = sheet.createRow(rowNum++);
          row.createCell(0).setCellValue(data.getXm1());
          row.createCell(1).setCellValue(data.getZkzh());
          row.createCell(2).setCellValue(data.getYw());
          row.createCell(3).setCellValue(data.getSx());
          row.createCell(4).setCellValue(data.getYy());
          row.createCell(5).setCellValue(data.getWl());
          row.createCell(6).setCellValue(data.getHx());
          row.createCell(7).setCellValue(data.getZs());
          row.createCell(8).setCellValue(data.getSd());
          row.createCell(9).setCellValue(data.getJf());
          row.createCell(10).setCellValue(data.getSycz());
          row.createCell(11).setCellValue(data.getTy());
          row.createCell(12).setCellValue(data.getZf());
        }

        // 保存工作簿到文件
        // 输出文件类型为xlsx
        FileOutputStream fileOut =
            new FileOutputStream(binding.outputTextInput.getText().toString());
        workbook.write(fileOut);

        // 关闭工作簿
        workbook.close();
        toast("导出成功");
      } else {
        toast("请先查询成绩，再导出数据");
      }
    } catch (Throwable error) {
      error.printStackTrace();
      toast("导出失败-" + error.toString());
    }
  }

  @Override
  protected int getRootViewResID() {
    // TODO: Implement this method
    return R.layout.fragment_batchquery;
  }

  @Override
  public String getFragmentName() {
    // TODO: Implement this method
    return "批量查询";
  }

  public String removeSpaces(TextInputEditText input) {
    return removeSpaces(input.getText().toString());
  }

  public String removeSpaces(String input) {
    if (input == null) {
      toast("禁止输入为空");
      return null;
    }
    return input.replaceAll("\\s", "");
  }

  public static String listToString(List<?> list) {
    return list.stream().map(Object::toString).collect(Collectors.joining(" "));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
