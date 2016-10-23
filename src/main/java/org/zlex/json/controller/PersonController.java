/**
 * Aug 10, 2012
 */
package org.zlex.json.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zlex.json.domain.Person;

/**
 *
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
@Controller
public class PersonController {

	/**
	 * 查询个人信息
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/person/profile/{id}/{name}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	Person porfile(@PathVariable int id, @PathVariable String name,
			@PathVariable boolean status) {
		return new Person(id, name, status);
	}

	/**
	 * 登录
	 *
	 * @return
	 */
	@RequestMapping(value = "/api/code/getCode", method = RequestMethod.GET)
	@ResponseBody
	public Person login() {
		return new Person();
	}
}
