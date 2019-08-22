package com.exntu.faceadmin.service;

import com.exntu.faceadmin.domain.Members;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Service for member
 * <p>
 * We use the {@link Async} annotation to member manages asynchronously.
 */
@Service
public class MatchService {

    private final Logger log = LoggerFactory.getLogger(MemberService.class);
    private String rootFolderPath;
    private String matchPath;

    public MatchService() {
        this.rootFolderPath = "/Users/kang-yechan/Desktop/frs/flask-frs/dataset/";
        this.matchPath = "/Users/kang-yechan/Desktop/frs/flask-frs/matchset/";
    }

    public HashMap<String, Object> getOriginImagePath(String name) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String memberPath = rootFolderPath + name;
        File matchMember = new File(memberPath);
        if(!matchMember.exists()) {
            log.error(name + " Member is not exists.");
        } else {
            File[] matchAllFileList = matchMember.listFiles();
            if(matchAllFileList != null) {
                for(File matchFile: matchAllFileList) {
                    if(isImageFile(matchFile.getName())){
                        hashMap.put("name", name);
                        hashMap.put("matchPath", "api/match/image-match?imagePath=" + name + ".jpg");
                        hashMap.put("getPath", "api/match/image-origin?imagePath=" + name + "/" + matchFile.getName());
                        break;
                    }
                }
            } else {
                log.error("MatchAllFileList is Null point Exception.");
            }
        }
        return hashMap;
    }

    private boolean isImageFile(String fileName) {
        return (fileName.toLowerCase().endsWith(".png") ||
            fileName.toLowerCase().endsWith(".jpg") ||
            fileName.toLowerCase().endsWith(".jpeg") ||
            fileName.toLowerCase().endsWith(".gif") ||
            fileName.toLowerCase().endsWith(".bmp") ||
            fileName.toLowerCase().endsWith(".tif") ||
            fileName.toLowerCase().endsWith(".tiff"));
    }

    public byte[] getImgSrc(String imagePath) throws IOException {
        FileInputStream fin = new FileInputStream(this.rootFolderPath + imagePath);
        return IOUtils.toByteArray(fin);
    }

    public byte[] getMatchImgSrc(String imagePath) throws IOException {
        FileInputStream fin = new FileInputStream(this.matchPath + imagePath);
        return IOUtils.toByteArray(fin);
    }
}