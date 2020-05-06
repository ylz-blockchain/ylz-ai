import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/mobile/"

export function getImagePageList(data) {
  return get(prefix + 'image/getImagePageList', data)
}

export function generateImage(data) {
  return post(prefix + 'image/generateImage', data)
}

export function changeImageById(data) {
  return put(prefix + 'image/changeImageById', data)
}

export function expurgateImageById(data) {
  return delWithParams(prefix + 'image/expurgateImageById', data)
}

export function expurgateImageBatch(data) {
  return delWithParams(prefix + 'image/expurgateImageBatch', data)
}

export function getImageById(data) {
  return get(prefix + 'image/getImageById', data)
}