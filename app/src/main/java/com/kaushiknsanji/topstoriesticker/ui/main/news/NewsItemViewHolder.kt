package com.kaushiknsanji.topstoriesticker.ui.main.news

import android.animation.Animator
import android.animation.AnimatorInflater
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.transition.TransitionManager
import com.bumptech.glide.request.RequestOptions
import com.kaushiknsanji.topstoriesticker.R
import com.kaushiknsanji.topstoriesticker.data.model.NewsArticle
import com.kaushiknsanji.topstoriesticker.di.component.ViewHolderComponent
import com.kaushiknsanji.topstoriesticker.ui.base.BaseItemViewHolder
import com.kaushiknsanji.topstoriesticker.utils.common.DateUtility
import com.kaushiknsanji.topstoriesticker.utils.common.GlideApp
import com.kaushiknsanji.topstoriesticker.utils.common.observeEvent
import com.kaushiknsanji.topstoriesticker.utils.display.TextAppearanceUtility
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * [BaseItemViewHolder] subclass used as [androidx.recyclerview.widget.RecyclerView.ViewHolder]
 * in [NewsAdapter].
 *
 * @param container [ViewGroup] that contains the ItemViews.
 * @param listener Instance of [NewsAdapter.Listener] to receive Navigation events.
 *
 * @author Kaushik N Sanji
 */
class NewsItemViewHolder(
    container: ViewGroup,
    listener: NewsAdapter.Listener?
) : BaseItemViewHolder<NewsArticle, NewsItemViewModel>(R.layout.item_main, container, listener) {

    // Rotate Animators for TextView Expand/Collapse ImageButton anchor
    lateinit var rotateTo180Anim: Animator
    lateinit var rotateTo0Anim: Animator

    /**
     * Injects dependencies exposed by [ViewHolderComponent] into [androidx.recyclerview.widget.RecyclerView.ViewHolder].
     */
    override fun injectDependencies(viewHolderComponent: ViewHolderComponent) {
        viewHolderComponent.inject(this)
    }

    /**
     * Initializes the Layout of the [itemView].
     */
    override fun setupView(itemView: View) {
        // Register a click listener on News Article Item
        itemView.setOnClickListener { itemViewModel.onItemClick() }

        // Register a click listener on the Expand/Collapse image button
        itemView.imgbtn_article_expand.setOnClickListener { itemViewModel.onItemExpandCollapseClick() }

        // Initialize the Animators for Expand/Collapse image button
        rotateTo180Anim = AnimatorInflater.loadAnimator(itemView.context, R.animator.rotate_0_180)
        rotateTo0Anim = AnimatorInflater.loadAnimator(itemView.context, R.animator.rotate_180_0)

        // Update the state of content expand button
        updateContentExpandButton()
    }

    /**
     * Method that updates the state of the Content Expand Button 'R.id.imgbtn_article_expand'
     * based on the presence of Trailing Text content and Author Text length
     */
    private fun updateContentExpandButton() {
        // Retrieve the Ellipse count for the Text in Author TextView
        val authorTextEllipseCount: Int = getEllipseCountFromAuthorTextView()

        if (authorTextEllipseCount > 0 || !TextUtils.isEmpty(itemView.text_article_trail.text)) {
            // Make the Content Expand Button visible when the Author Text is ellipsized
            // or the Title Trail Text is present
            itemView.imgbtn_article_expand.visibility = View.VISIBLE

            // Look for the Tag object to see if the image was rotated/expanded
            val contentExpandButtonTagObj = itemView.imgbtn_article_expand.tag
            if (contentExpandButtonTagObj != null && contentExpandButtonTagObj == true) {
                // Rotate the Image Anchor from 180 to 0 when the image was previously rotated/expanded
                val rotateTo0AnimImmediate: Animator = rotateTo0Anim.clone()
                rotateTo0AnimImmediate.apply {
                    duration = 0
                    setTarget(itemView.imgbtn_article_expand)
                    start()
                }
                // Reset the Tag to FALSE
                itemView.imgbtn_article_expand.tag = false

                // Reset the Max Lines on Author Text
                itemView.text_article_author.maxLines = itemView.context.resources.getInteger(
                    R.integer.text_article_author_max_lines_expanded
                )
            }
        } else {
            // Hide the Content Expand Button when the Author Text is NOT ellipsized
            // and the Title Trail Text is NOT present
            itemView.imgbtn_article_expand.visibility = View.GONE
        }
    }

    /**
     * Method that searches for the Ellipsis in the Text of Author TextView and returns its total count
     */
    private fun getEllipseCountFromAuthorTextView(): Int {
        var totalEllipseCount = 0 // Default the Total Ellipse count to 0, initially

        // Retrieve the layout attached to TextView
        val textViewLayout = itemView.text_article_author.layout

        // Iterate over the Lines of the Text and counting the Ellipsis found
        (0 until itemView.text_article_author.lineCount).forEach { lineIndex ->
            if (textViewLayout.getEllipsisCount(lineIndex) > 0) {
                totalEllipseCount++
            }
        }

        // Return the total Ellipsis found
        return totalEllipseCount
    }

    /**
     * Method that initializes the [androidx.lifecycle.LiveData] observers.
     * Can be overridden by subclasses to initialize other [androidx.lifecycle.LiveData] observers.
     */
    override fun setupObservers() {
        super.setupObservers()

        // Register an observer on the Section Name LiveData to set its value on corresponding textView
        itemViewModel.sectionName.observe(this, Observer { sectionName ->
            itemView.text_article_section.text = sectionName
        })

        // Register an observer on the Title LiveData to set its value on corresponding textView
        itemViewModel.title.observe(this, Observer { title ->
            itemView.text_article_title.text = title
        })

        // Register an observer on the Published Date LiveData to set its value on corresponding textView
        itemViewModel.publishedDate.observe(this, Observer { publishedDateStr ->
            itemView.text_article_published_date.text = DateUtility.getFormattedPublishedDate(
                publishedDateStr,
                itemView.resources.getString(R.string.message_default_no_published_date)
            )
        })

        // Register an observer on the Trail Text LiveData to set its value on corresponding textView
        itemViewModel.trailText.observe(this, Observer { trailText ->
            itemView.text_article_trail.text = TextAppearanceUtility.getHtmlFormattedText(trailText)
            // Update the state of content expand button
            updateContentExpandButton()
        })

        // Register an observer on the Author LiveData to set its value on corresponding textView
        itemViewModel.author.observe(this, Observer { author ->
            itemView.text_article_author.text = if (author.isNullOrEmpty()) {
                itemView.resources.getString(R.string.message_default_no_authors_found)
            } else {
                author
            }
        })

        // Register an observer on the Thumbnail LiveData to set its Image on corresponding ImageView
        itemViewModel.thumbnail.observe(this, Observer { thumbnailUrl ->
            itemView.run {
                if (!thumbnailUrl.isNullOrEmpty()) {
                    // When we have the Image Url

                    // Ensure the ImageView is shown
                    image_article_thumb.visibility = View.VISIBLE

                    // Download and set the Image using Glide
                    GlideApp.with(itemView) // Loading with ItemView's context
                        .load(thumbnailUrl) // Loading the URL of the Image for download
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_news_thumbnail)) // Loading with PlaceHolder Image
                        .into(image_article_thumb) // Start the download and load into the corresponding ImageView

                } else {
                    // When we do not have any Image Url

                    // Reset the Image to default on ImageView
                    image_article_thumb.setImageResource(R.drawable.ic_news_thumbnail)

                    // Hide the ImageView
                    image_article_thumb.visibility = View.GONE

                }
            }
        })

        // Register an observer on the User's Click action on News Article card
        itemViewModel.actionItemClick.observeEvent(this) { article: NewsArticle ->
            listener?.onItemClick(article)
        }

        // Register an observer on the User's Click action on Expand/Collapse anchor button
        itemViewModel.actionItemExpandCollapseClick.observeEvent(this) {
            toggleContentExpansion(
                itemView.imgbtn_article_expand,
                itemView.text_article_trail,
                itemView.text_article_author
            )
        }

    }

    /**
     * Method that expands/collapses the content of Title's Trailing Text and the Author Text of the Article
     * on click of the expand/collapse button 'R.id.imgbtn_article_expand'
     *
     * @param anchorButton [ImageButton] on the article card that expands/collapses the content
     * @param trailTextView [TextView] for the Title's Trailing Text
     * @param authorTextView [TextView] for the Author Text
     */
    private fun toggleContentExpansion(
        anchorButton: ImageButton,
        trailTextView: TextView,
        authorTextView: TextView
    ) {
        // Boolean to store whether the Trailing Text is expanded or not. Defaulted to FALSE (Collapsed)
        var trailTextExpandState = false

        if (!trailTextView.text.isNullOrEmpty()) {
            // Update the Trailing Text state only when there is Trailing Text Content
            trailTextExpandState = (trailTextView.visibility == View.VISIBLE)
        }

        // Retrieve the Max Lines count setting of the Author TextView in Collapsed state
        val authorTextCollapsedStateLineCountSetting: Int =
            itemView.context.resources.getInteger(R.integer.text_article_author_max_lines_collapsed)
        // Evaluate whether the Author Text is expanded (Line count is greater than the collapsed setting)
        val authorTextExpandState =
            authorTextView.lineCount > authorTextCollapsedStateLineCountSetting

        // Retrieve the parent RecyclerView to set up Transition Animations
        val containerViewGroup = trailTextView.parent.parent as ViewGroup

        if (trailTextExpandState || authorTextExpandState) {
            // Collapse/Hide the content as they are in expanded state

            // Hide the content of Trailing Text
            trailTextView.visibility = View.GONE

            // Apply the Transition Animation for collapse (After hiding the Trailing Text)
            TransitionManager.beginDelayedTransition(containerViewGroup)

            // Reset the Max Lines on Author Text
            authorTextView.maxLines = authorTextCollapsedStateLineCountSetting

            // Rotate the Image Anchor from 180 to 0
            rotateTo0Anim.apply {
                setTarget(anchorButton)
                start()
            }
            anchorButton.tag = false // Reset the Tag to FALSE
        } else {
            // Expand the content as they are in collapsed state

            // Apply the Transition Animation for expand
            TransitionManager.beginDelayedTransition(containerViewGroup)

            // Reveal the content of Trailing Text if any
            if (!trailTextView.text.isNullOrEmpty()) {
                trailTextView.visibility = View.VISIBLE
            }

            // Set the Max Lines on Author Text to a higher number for expanding content
            authorTextView.maxLines = itemView.context.resources
                .getInteger(R.integer.text_article_author_max_lines_expanded)

            // Rotate the Image Anchor from 0 to 180
            rotateTo180Anim.apply {
                setTarget(anchorButton)
                start()
            }
            // Set an Expanded State (TRUE) Identifier on the ImageButton's Tag
            anchorButton.tag = true
        }
    }

}