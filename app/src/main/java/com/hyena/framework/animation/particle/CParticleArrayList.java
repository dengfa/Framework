package com.hyena.framework.animation.particle;

/**
 * 粒子缓冲集合
 * @author yangzc
 *
 */
public class CParticleArrayList {

	//粒子集合
    private CParticle[] mParticles;
    private int mSize = 1024;

    public CParticleArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.mSize = capacity;
        mParticles = new CParticle[capacity];
    }

    public CParticleArrayList() {
    	mParticles = new CParticle[mSize];
    }

    /*
     * 添加粒子
     */
    public boolean add(CParticle particle) {
        if(mParticles != null){
        	for(int i=0; i< mParticles.length; i++){
        		if(mParticles[i] == null
        				|| !mParticles[i].isAlive()){
        			mParticles[i] = particle;
        			return true;
        		}
        	}
        }
        return false;
    }

    /**
     * 删除粒子
     */
    public void clear() {
    	if(mParticles != null){
        	for(int i=0; i< mParticles.length; i++){
        		mParticles[i] = null;
        	}
        }
    }
    
    /*
     * 粒子数
     */
    public int size(){
    	return mSize;
    }

    /*
     * 获得粒子
     */
    public CParticle get(int index){
    	if(index >= mParticles.length
    			|| index < 0)
    		return null;
    	
    	return mParticles[index];
    }
}
