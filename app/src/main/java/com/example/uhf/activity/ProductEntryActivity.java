package com.example.uhf.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.uhf.R;
import com.example.uhf.tools.StringUtils;
import com.example.uhf.view.CustomChip;
import com.google.android.material.chip.ChipGroup;
import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProductEntryActivity extends Activity implements View.OnClickListener {

    private RFIDWithUHFUART mReader;
    private HashSet<String> epcSet = new HashSet<>();

    private boolean isScanning = false;

    private Button btnScan, btnCancelAll, btnContinue;
    private ChipGroup chipGroupTags;
    private LinearLayout llNavigationButtons;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 && msg.obj instanceof UHFTAGInfo) {
                UHFTAGInfo info = (UHFTAGInfo) msg.obj;
                addTag(info);
                if (playSoundThread != null) {
                    playSoundThread.play();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_entry);

        initViews();
        initSound();
        initDevice();
    }

    private void initViews() {
        // Header
        ImageView ivActionIcon = findViewById(R.id.iv_action_icon);
        ivActionIcon.setImageResource(R.drawable.ic_back);
        ivActionIcon.setOnClickListener(v -> finish());

        // Main content
        btnScan = findViewById(R.id.btnScan);
        btnCancelAll = findViewById(R.id.btnCancelAll);
        btnContinue = findViewById(R.id.btnContinue);
        chipGroupTags = findViewById(R.id.chipGroup_tags);
        llNavigationButtons = findViewById(R.id.ll_navigation_buttons);

        btnScan.setOnClickListener(this);
        btnCancelAll.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    private void initDevice() {
        new InitTask().execute();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnScan) {
            toggleScan();
        } else if (id == R.id.btnCancelAll) {
            showCancelConfirmationDialog();
        } else if (id == R.id.btnContinue) {
            goToNextStep();
        }
    }

    private void goToNextStep() {
        if (epcSet.isEmpty()) {
            Toast.makeText(this, "هیچ کالایی برای ثبت وجود ندارد.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(ProductEntryActivity.this, NameProductActivity.class);
        intent.putStringArrayListExtra("SCANNED_TAGS", new ArrayList<>(epcSet));
        startActivity(intent);
    }

    private void toggleScan() {
        if (!isScanning) {
            startScan();
        } else {
            stopScan();
        }
    }

    private void startScan() {
        if (mReader == null) {
            Toast.makeText(this, "دستگاه آماده نیست", Toast.LENGTH_SHORT).show();
            return;
        }
        epcSet.clear();
        updateChipGroup();
        if (mReader.startInventoryTag()) {
            isScanning = true;
            btnScan.setText("توقف");
            llNavigationButtons.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "شروع اسکن ناموفق بود", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopScan() {
        if (mReader != null) {
            mReader.stopInventory();
        }
        isScanning = false;
        btnScan.setText("اسکن");
        if (!epcSet.isEmpty()) {
            llNavigationButtons.setVisibility(View.VISIBLE);
        }
    }

    private void addTag(UHFTAGInfo info) {
        String epc = info.getEPC();
        if (StringUtils.isNotEmpty(epc) && epcSet.add(epc)) {
            runOnUiThread(this::updateChipGroup);
        }
    }

    private void updateChipGroup() {
        chipGroupTags.removeAllViews();
        for (String epc : epcSet) {
            CustomChip chip = new CustomChip(this);
            chip.setText(epc);
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> {
                epcSet.remove(epc);
                updateChipGroup();
            });
            chipGroupTags.addView(chip);
        }
        if (!isScanning && !epcSet.isEmpty()) {
            llNavigationButtons.setVisibility(View.VISIBLE);
        } else {
            llNavigationButtons.setVisibility(View.GONE);
        }
    }

    private void showCancelConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("تایید انصراف")
                .setMessage("آیا از پاک کردن تمام کالاهای اسکن شده مطمئن هستید؟")
                .setPositiveButton("بله", (dialog, which) -> {
                    epcSet.clear();
                    updateChipGroup();
                })
                .setNegativeButton("خیر", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        if (isScanning) {
            stopScan();
        }
        releaseSoundPool();
        if (mReader != null) {
            mReader.free();
        }
        super.onDestroy();
    }

    // --- Sound Logic ---
    private HashMap<Integer, Integer> soundMap = new HashMap<>();
    private SoundPool soundPool;
    private PlaySoundThread playSoundThread;

    private void initSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(this, R.raw.barcodebeep, 1));
        playSoundThread = new PlaySoundThread();
        playSoundThread.start();
    }

    private void releaseSoundPool() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        if (playSoundThread != null) {
            playSoundThread.stopPlay();
        }
    }

    public void playSound(int id) {
        if (soundPool != null) {
            soundPool.play(soundMap.get(id), 1, 1, 1, 0, 1);
        }
    }

    private class PlaySoundThread extends Thread {
        private boolean isStop = false;
        private ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        public void play() { if (!isStop) queue.offer(1); }
        public void stopPlay() { isStop = true; }

        @Override
        public void run() {
            while (!isStop) {
                Integer val = queue.poll();
                if (val != null) playSound(val);
                try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    // --- AsyncTask for Device Initialization ---
    public class InitTask extends AsyncTask<Void, String, Boolean> {
        ProgressDialog myDialog;

        @Override
        protected void onPreExecute() {
            myDialog = new ProgressDialog(ProductEntryActivity.this);
            myDialog.setMessage("در حال آماده سازی دستگاه...");
            myDialog.setCancelable(false);
            myDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                mReader = RFIDWithUHFUART.getInstance();
                if (mReader.init(ProductEntryActivity.this)) {
                    mReader.setPower(1);
                    mReader.setInventoryCallback(info -> handler.sendMessage(handler.obtainMessage(1, info)));
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            myDialog.dismiss();
            if (!result) Toast.makeText(ProductEntryActivity.this, "اتصال به دستگاه ناموفق بود!", Toast.LENGTH_SHORT).show();
        }
    }
}