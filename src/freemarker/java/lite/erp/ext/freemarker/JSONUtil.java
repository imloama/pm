package lite.erp.ext.freemarker;

import java.util.List;

import com.alibaba.fastjson.JSON;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class JSONUtil implements TemplateMethodModel{

	@Override
	public Object exec(List list) throws TemplateModelException {
		return JSON.toJSONString(list);
	}

}
