package com.onlyapps.volleyimagesample;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.onlyapps.volleyimageloader.ApiVolleyImage;
import com.onlyapps.volleyimageloader.VolleyImageCacheManager;
import com.onlyapps.volleyimageloader.VolleyImageLoader;
import com.onlyapps.volleyimageloader.view.VolleyImageView;

/**
 * List ImageAdapter
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private VolleyImageLoader mImageLoader;
    
    private int mLastPosition;
    
	public ImageAdapter(Context context) {
	    this.mContext = context;
	    this.mImageLoader = VolleyImageCacheManager.getInstance().getImageLoader();
	}
	
	@Override
	public int getCount() {
		return IMAGE_URLS.length;
	}

	@Override
	public String getItem(int position) {
		return IMAGE_URLS[position];
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
        ViewHolder vh = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.volley_image_list_item, null);
            vh = new ViewHolder();
            vh.image = (VolleyImageView) view.findViewById(R.id.image);
            // NetworkImageView add API
            vh.image.setBitmapConfig(Config.ARGB_8888);
            vh.image.setFitToScreen(true);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.image.setImageUrl(getItem(position), mImageLoader);
	    
        // Improving speed logic
        loadingImageInAdvance(position, mLastPosition >= position);
        mLastPosition = position;
        
        return view;
    }
	
	private static class ViewHolder {
        public VolleyImageView image;
    }
	
	/**
     * 
     * @param position
     * @param isDownScroll
     */
    private void loadingImageInAdvance(int position, boolean isDownScroll) {
        final int INTERVAL_INDEX = 5;
        final int COUNT = getCount() - 1;
        
        int index = 0;
        for (int i = position; index <= INTERVAL_INDEX;) {
            if (i > COUNT || i < 0) {
                break;
            }
            ApiVolleyImage.load(getItem(i), Config.ARGB_8888);
            if (isDownScroll) {
                i--;
            } else {
                i++;
            }
            index++;
        }
    }
	
	private final String[] IMAGE_URLS = {
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2w-GcXzrZwtHnbpxZ5eZr31h59BkEeqaM79CMQlK_gCFXlrRyzA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2w-ZmDUc_nhbh2pd64iSKvC_kR_p410FamuG8XkhZl5ByyJXqMg/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2w-WyqRDbUiaJAm34VCbm3CGYydWgW7uk4oyAZb8-4-zKEpMcdg/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2wjs0H6P494oOD-xTmWllSy9I1cCEbgcg6Gpbga5xTPqGFftdqg/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2wjmr83pWy-Lu17hQdaTDqG4wFwaKjGeSS4fCN77_5-l3GRbp4Q/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2wjhvmfZJgSJlIdge1f4Jhc4Ssg1BNMPVca6RO2XygBI4UuvcCw/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2xZkQ6Td0IbDAuaLf4n7tfWek7t5hTETNQrherNXGCQj4q3FwHA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qULS2Lj9RKhmKieAzvEQjiGlsITxuTy7k7KXfszKJE2eS3wqHYA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2zsSXQA3-SyyIqSPOPgrh-yROn091Y9-OY6usi5AzTl0XJTek-w/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2z4EAnzEmvypgDxsuwAlPXIS1U1OFn6ywK9gDpxfOJGFtQceYKQ/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2wEBRsB1IjYX29lLNu1CU1zRHPgxpJgGt2fFAPelFh4tzshsa4w/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qULJ3NumRn70_dOxVQVEhJjY6sK146mvgSrBX78MF7x1-jQElBg/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2wT2HUgee6YK5zd5nbqeCe-X391yUjxTdCq5c9JtULCxabPd0fQ/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2zs5CpQwVlJtCBWlTFmTRmDU-Y_53UauLNe_MGQaMLNvoyJayww/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2zsSUZHSm_WjmR3zsmA02FZ4_kofkaGcLbGCPXesV7Iuk0hh6aQ/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoXI2zsXRCHLvweAc64dXqhA6Cgf5Rn8MTo4I59KCzO4X1MmfPewbQw/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qVisnEW07y09WQ8ZYyglvps2PERiUgAXVgm0WjQpRzpKBMhKLoA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qU--oxi4aU4TEngnSl_2gABDAfXqu-qQ8FQT_J_Nlx34wXfnjDg/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qVismtAw0cxBkUgT9WZm-1stn5w753HnbWKkasvjBxZc4M-TwaA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qVeE-UBwF9iH24jQHkqZjVuhBOMXVX6-hSr5_16pdtC4UB-fICA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qViHMFGWDQCc7OEwe7-Jm5OA0LBFkwTtVeCx4p53Iu1UEvzYLcg/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qV-JuhVWMWCs2jSEcGkTxAmUJWK0PuNF-FmHs85_EfFtUT1wuqA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2qV-840RDJ34-6aqlrPN7Q90U9xP7l7xRJsL8_cmyoRWjBumAduQ/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2rmZMxBwARGEG9lnvGa1QSK04EP_xIvQMEbNiWLvYPRbEjjoup7A/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2rna5_7Z2c94sUFjAQdLZNrvuWtBb1nONoT3Xg1BGwIRosv9bcSQ/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2rnmHnVIvenvvugXGnf_xyCvwRW0Ilvc7-ml98vbjbdx1zqv83lA/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2rnxxQiyIZrRR9WaMKvVLR51ixTtfb3d5wC60S3HAI7FWEOdzeUw/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2rna55T8ybLvpaJLN2_GW-4LTIbGbiXd6MQ0FEe04TmbaBe-NfyQ/",
        "http://photography.nationalgeographic.com/u/TvyamNb-BivtNwcoxtkc5xGBuGkIMh_nj4UJHQKuoX2rna57S8CtFAlJXBwCfo6TuFbWgZl0UGgRKoeAaRnvR1IllCo8zA/",
        "http://cfile25.uf.tistory.com/image/2441483F53A033723B2E66",
        "http://img.imbc.com/broad/tv/drama/jangbori/cast/cast0/cast03/__icsFiles/afieldfile/2014/04/14/cont_cast03.jpg",
        "http://cfile3.uf.tistory.com/image/2341483F53A033723ADD10", "http://i.imgur.com/HaouSvg.gif",
        "http://cfile2.uf.tistory.com/image/237778345420D9420602A6", "http://cfile8.uf.tistory.com/image/276D0C46543128B43A5982",
        "http://static.news.zumst.com/images/52/2014/09/08/11f7724e39c04ac1b9e055a55b78e85f.jpg",
        "http://static.news.zumst.com/images/3/2014/08/10/AKR20140808074100005_02_i.jpg",
        "http://img.tenasia.hankyung.com/webwp_kr/wp-content/uploads/2014/08/2014083123460813469.jpg",
        "http://www.wikitree.co.kr/webdata/editor/201409/15/img_20140915091818_e4bf3fb5.jpg",
        "http://tenasia.hankyung.com/webwp_kr/wp-content/uploads/2014/09/2014091322544516438.jpg", "http://pbs.twimg.com/media/Bx8csj_CIAEyCJo.jpg",
        "http://i.imgur.com/4OUIAvG.jpg", "http://ojsfile.ohmynews.com/STD_IMG_FILE/2014/0831/IE001748982_STD.jpg",
        "http://cfile25.uf.tistory.com/image/11339939506020DF2CA4FE",
        "https://lh4.googleusercontent.com/-aGbeVfRSCt0/UJk4E31HfgI/AAAAAAAA6Cc/RwWpiNjnrk0/w497-h373/moyu.png",
        "http://oimg.filejo.com/main/data/2012/08/18/5nG0q_1345256150.jpg", "http://cfile5.uf.tistory.com/image/1778AC4C5018F8D9109386",
        "http://upload.barobook.com/ijakga/series/2009/08/28/001.jpg", "http://img506.imageshack.us/img506/9207/albumcover22442cdafd01fz7.jpg",
        "http://i.imgur.com/9MQx44O.jpg",
        "http://image.wemakeprice.com//dealimg/201501/429367/feb0d901e4f4357380872a08c79f3fbb61d5bad4_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/427622/4e48efa76346e6f0a230bf80a5234712e3cac18c_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/427189/94321abc5391dfe3bbe894ff51083981c16b7e46_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/427189/94321abc5391dfe3bbe894ff51083981c16b7e46_slice_1.jpg",
        "http://image.wemakeprice.com//dealimg/201501/427189/94321abc5391dfe3bbe894ff51083981c16b7e46_slice_3.jpg",
        "http://image.wemakeprice.com//dealimg/201501/427189/94321abc5391dfe3bbe894ff51083981c16b7e46_slice_4.jpg",
        "http://image.wemakeprice.com//dealimg/201501/427189/94321abc5391dfe3bbe894ff51083981c16b7e46_slice_5.jpg",
        "http://image.wemakeprice.com//dealimg/201501/430359/6f49259770871bc56f715349f8af861ac8d0a532_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/430359/6f49259770871bc56f715349f8af861ac8d0a532_slice_1.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/04cea9337494ebfc27f4ef56a0c57e17aacf762c_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/04cea9337494ebfc27f4ef56a0c57e17aacf762c_slice_1.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/04cea9337494ebfc27f4ef56a0c57e17aacf762c_slice_2.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_1.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_2.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_3.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_4.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_5.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_6.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/e59581e6213455898e347298e41b968e697bd5bc_slice_7.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_1.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_2.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_3.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_4.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_5.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_6.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_7.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_8.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/25b21794694bf28899689c0a59c48a0556b8b469_slice_9.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_0.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_1.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_2.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_3.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_4.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_5.jpg",
        "http://image.wemakeprice.com//dealimg/201501/425114/45f23507ecc0a6ceff1c1b122ba418b86296668c_slice_6.jpg"
	};
}
