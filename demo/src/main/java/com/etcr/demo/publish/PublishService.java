package com.etcr.demo.publish;

import com.etcr.demo.File.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import java.util.Base64.*;

@Transactional
@Service
public class PublishService {
    @Autowired
    private PublishRepository publishRepository;
    public boolean publish(String cur_name, String cur_user_id, String cur_type, String cur_time, String cur_money, String cur_phone, String cur_detail,String cur_picture)
    {
        Merchandise cur_mer=new Merchandise();
        cur_mer.setTime(cur_time);
        cur_mer.setContact(cur_phone);
        cur_mer.setTitle(cur_name);
        cur_mer.setInfo(cur_detail);
        cur_mer.setPrice(cur_money);
        cur_mer.setKind(cur_type);
        cur_mer.setPost_userid(cur_user_id);

        /*base64转存图片策略
        String dataPrix = "";
        String data = "";
        if(cur_picture == null || "".equals(cur_picture)){
            return  false;
        }else {
            String[] d = cur_picture.split("base64,");
            if (d != null && d.length == 2) {
                //dataPrix = d[0];
                data = d[1];
            }
        }
        String suffix = ".png";
        if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if("data:image/png;".equalsIgnoreCase(dataPrix)) {//data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }else
        {
            return false;
        }
        String tempfileName = UUID.randomUUID().toString()+suffix;
        String imgfilePath = "/root/merch_image/"+tempfileName;
        Decoder decoder= Base64.getDecoder();
        byte[] b =  decoder.decode(data);
        try {
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgfilePath);
            out.write(b);
            out.flush();
            out.close();
            String imgurl = "http://121.43.181.253/" + tempfileName;
            cur_mer.setImage(imgurl);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }*/
        //将bitmap直接存入txt文件中
        String suffix=".txt";
        String tempfileName = UUID.randomUUID().toString()+suffix;//uuid生成唯一文件名
        String imgfilePath = "/root/merch_image/"+tempfileName;
        FileOperation file=new FileOperation();
        if(file.writefile(imgfilePath,cur_picture))
        {
            cur_mer.setPicturechar(imgfilePath);
        }
        publishRepository.save(cur_mer);
        return true;
    }


    public List<Merchandise> All_mer()
    {
        FileOperation file=new FileOperation();
        Iterable<Merchandise> tmp=publishRepository.findAll();
        List<Merchandise> list = new ArrayList<>();
        tmp.forEach(list::add);
        List<Merchandise> res_list = null;
        try {
            res_list = file.deepCopy(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i< res_list.size();i++) {
            String img="";
            Merchandise res;
            res = res_list.get(i);
            img=file.readfile(res.getPicturechar());
            res.setPicturechar(img);
            res_list.set(i,res);
        }
        return res_list;
    }

    public List<Merchandise> kind_mer(String kind)
    {
        FileOperation file=new FileOperation();
        List<Merchandise> list=publishRepository.findBykind(kind);
        List<Merchandise> res_list = null;
        try {
            res_list = file.deepCopy(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i< res_list.size();i++) {
            String img="";
            Merchandise res;
            res = res_list.get(i);
            img=file.readfile(res.getPicturechar());
            res.setPicturechar(img);
            res_list.set(i,res);
        }
        return res_list;
    }

    public boolean delete(String title)
    {
        publishRepository.deleteBytitle(title);
        return true;
    }

    public List<Merchandise> My_mer(String cur_id)
    {
        FileOperation file=new FileOperation();
        List<Merchandise> list=publishRepository.findByUser(cur_id);
        List<Merchandise> res_list = null;
        try {
            res_list = file.deepCopy(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i< res_list.size();i++) {
            String img="";
            Merchandise res;
            res = res_list.get(i);
            img=file.readfile(res.getPicturechar());
            res.setPicturechar(img);
            res_list.set(i,res);
        }
        return res_list;
    }

    public Iterable<Merchandise> getAllmer() {return publishRepository.findAll();}
}
