package com.jarvismall.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jarvismall.mapper.TbItemCatMapper;
import com.jarvismall.mapper.TbItemDescMapper;
import com.jarvismall.mapper.TbItemMapper;
import com.jarvismall.pojo.*;
import com.jarvismall.service.TbItemService;
import com.jarvismall.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JarvisDong on 2018/8/16.
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private JmsTemplate template;

//    @Autowired
    @Resource(name = "activeMQTopic")
    private Destination destination;

    @Override
    public List<TbItem> selectAll() {
        return null;
    }

    @Override
    public TbItem getItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }

    @Override
    public EasyUiDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);

        TbItemExample example = new TbItemExample();
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        int i = tbItemMapper.countByExample(example);
        System.out.print("count:" + i);
        Page<TbItem> pageBean = new Page<TbItem>(page, rows, i);

        return new EasyUiDataGridResult((int) pageBean.getTotal(), tbItems);
    }

    @Override
    public List<EasyUiTreeNode> getTreeNodeByParentId(long id) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
        List<EasyUiTreeNode> nodes = new ArrayList<>();
        for (TbItemCat cat : tbItemCats) {
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setText(cat.getName());
            node.setId(cat.getId());
            //有子节点closed,没有子节点open;
            node.setState(cat.getIsParent() ? "closed" : "open");
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {
        final long id = IDUtils.genItemId();
        item.setId(id);
        item.setUpdated(new Date());
        item.setCreated(new Date());
        //1-正常,2-下架,3-删除
        item.setStatus((byte) 1);
        int num1 = tbItemMapper.insert(item);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemId(id);
        int num2 = tbItemDescMapper.insert(tbItemDesc);

        TaotaoResult result = null;
        if (num1 != 0 && num2 != 0) {
            //使用activiemq发送添加商品消息;
            template.send(destination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    ActivieMqBundle bundle = new ActivieMqBundle(id+"",null);
                    TextMessage textMessage = session.createTextMessage(ActivieMqBundle.createJson(bundle));
                    return textMessage;
                }
            });

            result = TaotaoResult.build(200, "success");
        } else {
            result = TaotaoResult.build(400, "fail");
        }
        return result;
    }
}
