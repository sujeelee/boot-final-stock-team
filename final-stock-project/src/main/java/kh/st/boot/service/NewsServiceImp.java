package kh.st.boot.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import kh.st.boot.dao.NewsDAO;
import kh.st.boot.model.vo.FileVO;
import kh.st.boot.model.vo.MemberVO;
import kh.st.boot.model.vo.NewsEmojiVO;
import kh.st.boot.model.vo.NewsPaperVO;
import kh.st.boot.model.vo.NewsVO;
import kh.st.boot.utils.UploadFileUtils;
import lombok.AllArgsConstructor;

@Service
public class NewsServiceImp implements NewsService{
	
	@Autowired
	private NewsDAO newsDao;
	
	@Value("${file.upload-dir}")
	String uploadPath;
	
	@Override
	public List<NewsPaperVO> getNewsPaperList() {
		return newsDao.selectNewsPaperList();
	}
	
	@Override
	public List<NewsVO> getNewsList(Date ne_datetime) {
		if(ne_datetime == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String formatDate = format.format(ne_datetime);
		return newsDao.selectNewsListByDate(formatDate);
	}

	@Override
	public NewsPaperVO getNewsPaper(int np_no) {
		return newsDao.selectNewsPaper(np_no);
	}

	@Override
	public List<NewsVO> getNewsList(int np_no, String ne_datetime) {
		String regex = "^\\d{4}-\\d{2}-\\d{2}$";
		if(!Pattern.matches(regex, ne_datetime)) {
			return null;
		}
		return newsDao.selectNewsListByPaper(np_no, ne_datetime);
	}

	@Override
	public NewsVO getNews(int ne_no) {
		return newsDao.selectNews(ne_no);
	}

	@Override
	public NewsEmojiVO getNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return null;
		}
		return newsDao.selectNewsEmoji(emoji);
	}

	@Override
	public void insertNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return;
		}
		try {
			newsDao.insertNewsEmoji(emoji);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateNewsEmojiCount(NewsEmojiVO emoji, int count) {
		if(emoji == null) {
			return;
		}
		newsDao.updateNewsEmojiCount(emoji, count);
	}

	@Override
	public void updateNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return;
		}
		newsDao.updateNewsEmoji(emoji);
	}
	
	@Override
	public void deleteNewsEmoji(NewsEmojiVO emoji) {
		if(emoji == null) {
			return;
		}
		newsDao.deleteNewsEmoji(emoji);
	}
	
	@Override
	public boolean insertNews(NewsVO news, MemberVO user, MultipartFile file) {
		if(news == null || user == null) {
			return false;
		}
		if(news.getNe_title().trim().length() == 0) {
			return false;
		}
		if(news.getNe_content().trim().length() == 0) {
			return false;
		}
		news.setMb_id(user.getMb_id());
		boolean res = newsDao.insertNews(news);
		if(!res) {
			return false;
		}
		
		NewsVO tmp_news = newsDao.selectNewsLimitOne();
		uploadFile(file, tmp_news.getNe_no());
		return true;
	}


	private void uploadFile(MultipartFile file, int ne_no) {
		if(file == null || file.getOriginalFilename().length() == 0) {
			return;
		}
		// 첨부 파일을 서버에 업로드
		String fi_org_name = file.getOriginalFilename();
		try {
			String fi_path = UploadFileUtils.uploadFile(uploadPath, fi_org_name, file.getBytes());
			// 업로드한 정보를 DB에 추가
			FileVO fileVo = new FileVO(fi_path, fi_org_name, ne_no, "news");
			newsDao.insertFile(fileVo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean updateNews(NewsVO news, MemberVO user, MultipartFile file, Integer num) {
		if(news == null || user == null) {
			return false;
		}
		if(news.getNe_title().trim().length() == 0) {
			return false;
		}
		if(news.getNe_content().trim().length() == 0) {
			return false;
		}
		NewsVO tmp = getNews(news.getNe_no());
		if(!tmp.getMb_id().equals(user.getMb_id())) {
			return false;
		}
		boolean res = newsDao.updateNews(news);
		if(!res) {
			return false;
		}
		uploadFile(file, news.getNe_no());
		if(num == null) {
			return true;
		}
		FileVO tmp_news = newsDao.selectFileByFiNo(num);
		deleteFile(tmp_news);
		return true;
	}

	private void deleteFile(FileVO file) {
		if(file == null) {
			return;
		}
		UploadFileUtils.delteFile(uploadPath, file.getFi_path());
		newsDao.deleteFile(file.getFi_no());
	}

	@Override
	public FileVO getFile(int ne_no) {
		return newsDao.selectFileByNeNo(ne_no);
	}

	@Override
	public boolean deleteNews(int ne_no, MemberVO user) {
		if(user == null) {
			return false;
		}
		NewsVO post = getNews(ne_no);
		if(!post.getMb_id().equals(user.getMb_id())) {
			return false;
		}
		return newsDao.deleteNews(ne_no);
	}

}
