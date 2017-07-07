package com.hyena.framework.animation.particle;

import java.util.ArrayList;
import java.util.List;

import com.hyena.framework.animation.Director;
import com.hyena.framework.animation.texture.CTexture;

import android.graphics.Point;

/**
 * 旋律粒子效果
 * @author yangzc
 */
public class CMelodyStyle extends CParticleSystem {
	private final static int MAX_SPRITE_CNT = 60;
	private final static int SPREAD_ROUND_STEP = 20;
	private final static int SPREAD_BELOW_BEZIER_STEP = 20;
	private final static int SPREAD_ABOVE_BEZIER_STEP = 100;

	private List<CTexture> mTextures;
	
	protected CMelodyStyle(Director director, CTexture ...textures){
		super(director, 120);
		mTextures = new ArrayList<CTexture>();
		if(textures != null){
			for(int i=0; i< textures.length; i++){
				mTextures.add(textures[i]);
			}
		}
	}

	public static CMelodyStyle create(Director director, CTexture ...textures){
		return new CMelodyStyle(director, textures);
	}
	
	@Override
	public synchronized void update(float dt) {
		super.update(dt);
	}
	
	/*
	 * 反馈精灵刷新
	 */
	public void updateStars(Director director, int level){
		if(mTextures == null || mTextures.size() == 0)
			return;
		
		Point position = getPosition();
		if(position == null)
			return;
		
		for(int i=0; i < level; i++){
			CTexture texture = mTextures.get(getRandomObj().nextInt(mTextures.size()));
			List<Point> paths = schedulePath(position, i);
			if(paths != null){
				int duration = 3000;
				if(paths != null && paths.size() == SPREAD_ROUND_STEP + SPREAD_BELOW_BEZIER_STEP){
					duration = (int) (1000 + Math.random() * 1000);
				}else{
					duration = (int) (1000 + Math.random() * 4000);
				}
				CParticle sprite = CParticle.create(director, getRecycleParticle(), texture, position, paths, duration);
				if(sprite != null)
					addParticle(sprite);
			}
		}
	}
		
	/**
	 * 规划路径
	 * @param index
	 * @return
	 */
	private List<Point> schedulePath(Point position, int index){
		if(position == null)
			return null;
		
		List<Point> paths = new ArrayList<Point>();
		double incream = Math.PI/(2 * MAX_SPRITE_CNT);
		
		double angle = incream * index + 3 * Math.PI/4;
		int radius = 0;
		Point point = new Point();
		for(int i=0; i< SPREAD_ROUND_STEP; i++){
			double x = radius * Math.cos(angle) + position.x;
			double y = -radius * Math.sin(angle) + position.y;
			radius += 2;
			
			point = new Point((int)x + index, (int)y);
			paths.add(point);
		}
		
		if(angle < Math.PI){
			int endX = (int) (Math.random() * (getWidth()*3/4)) + getWidth()/4;
			int endY = 0;
			Point end = new Point(endX, endY);
			
			int ctrlX = (int) (-dip2px(40) - dip2px(180) * Math.random());
			int ctrlY = (int) (point.y/2 + (dip2px(20) * Math.random()));
			Point ctrlPoint = new Point(ctrlX, ctrlY);
			
			paths.addAll(calculateBezierPaths(point, ctrlPoint, end, SPREAD_ABOVE_BEZIER_STEP));	
		}else{
			int endX = 0;
			int endY = getHeight();
			Point end = new Point(endX, endY);
			
			int ctrlX = (int) (Math.random() * position.x/2) - dip2px(40);
			int ctrlY = (int) ((getHeight() - position.y)/2 * Math.random() + position.y);
			Point ctrlPoint = new Point(ctrlX, ctrlY);
			
			paths.addAll(calculateBezierPaths(point, ctrlPoint, end, SPREAD_BELOW_BEZIER_STEP));
		}
		
		return paths;
	}
	
}
