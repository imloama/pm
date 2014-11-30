package lite.erp.system.controller;

import java.util.List;

import lite.erp.system.consts.MessageCode;
import lite.erp.system.service.IOriganizationService;
import lite.erp.system.vo.Message;
import lite.erp.system.vo.OriganizationVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 部门管理类
 * @author mazhaoyong
 *
 */
@Controller
public class OriganizationController {

	@RequestMapping("/origs")
	@ResponseBody
	public Message findAll(){
		List<OriganizationVO> result = this.getOrigService().findAllVOs();
		Message msg = new Message();
		msg.setCode(MessageCode.SUCCESS).setData(result);
		return msg;
	}
	
	@RequestMapping(value="/orig/{id}",method={RequestMethod.GET})
	@ResponseBody
	public Message find(@PathVariable("id") Long id){
		 Message msg = new Message();
		 msg.setCode(MessageCode.SUCCESS).setData(getOrigService().findVO(id));
		 return msg;
	}
	
	@RequestMapping(value="/orig/update",method={RequestMethod.POST})
	@ResponseBody
	public Message updatevo(@RequestBody OriganizationVO orig){
		OriganizationVO origvo = this.getOrigService().update(orig);
		return new Message().setCode(MessageCode.SUCCESS).setData(origvo);
	}
	
	@RequestMapping(value="/orig",method={RequestMethod.POST})
	@ResponseBody
	public Message insert(@RequestBody OriganizationVO orig){
		OriganizationVO origVO = this.getOrigService().insert(orig);
		return new Message().setCode(MessageCode.SUCCESS).setData(origVO);
	}
	
	
	
	@RequestMapping(value="/orig/del/{id}",method={RequestMethod.GET})
	@ResponseBody
	public Message delete(@PathVariable("id") Long id){
		this.getOrigService().delete(id);
		return new Message().setCode(MessageCode.SUCCESS);
	}

	
	
	@Autowired
	private IOriganizationService origService;
	
	
	public IOriganizationService getOrigService() {
		return origService;
	}
	
	public void setOrigService(IOriganizationService origService) {
		this.origService = origService;
	}
	
}
