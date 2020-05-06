import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/mobile/"

export function getImageCommentPageList(data) {
  return get(prefix + 'imageComment/getImageCommentPageList', data)
}

export function generateImageComment(data) {
  return post(prefix + 'imageComment/generateImageComment', data)
}

export function changeImageCommentById(data) {
  return put(prefix + 'imageComment/changeImageCommentById', data)
}

export function expurgateImageCommentById(data) {
  return delWithParams(prefix + 'imageComment/expurgateImageCommentById', data)
}

export function expurgateImageCommentBatch(data) {
  return delWithParams(prefix + 'imageComment/expurgateImageCommentBatch', data)
}

export function getImageCommentById(data) {
  return get(prefix + 'imageComment/getImageCommentById', data)
}